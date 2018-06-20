package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

/*
*类名和方法不能修改
 */
public class Schedule {

    private List<Integer> nodes = new ArrayList<Integer>();

    private List<Integer> tasks = new ArrayList<Integer>();

    private Map<Integer,List<TaskInfo>> taskStatus = new HashMap<Integer, List<TaskInfo>>();

    private Map<Integer,Integer> taskMap = new HashMap<Integer,Integer>();

    private Map<Integer,List<Integer>> sameTasks = new HashMap<Integer, List<Integer>>();


    Comparator<TaskInfo> comparator = new Comparator<TaskInfo>()
    {
        public int compare(TaskInfo o1, TaskInfo o2)
        {
            return (o1.getTaskId()-o2.getTaskId());
        }
    };


    Comparator<Integer> comparatorByTime = new Comparator<Integer>()
    {
        public int compare(Integer o1, Integer o2) {
            return (taskMap.get(o2)-taskMap.get(o1));
        }
    };


    public int init() {
        taskStatus.clear();
        taskMap.clear();
        sameTasks.clear();
        return ReturnCodeKeys.E001;
    }

    /**
     * 注册节点
     * @param nodeId
     * @return
     */
    public int registerNode(int nodeId) {
        //如果服务节点编号小于等于0, 返回E004:服务节点编号非法。
        if(nodeId <= 0)
        {
            return ReturnCodeKeys.E004;
        }
        //如果服务节点编号已注册, 返回E005:服务节点已注册。
        if(nodes.contains(nodeId))
        {
            return ReturnCodeKeys.E005;
        }
        //注册成功，返回E003:服务节点注册成功。
        nodes.add(nodeId);

        Collections.sort(nodes);

        return ReturnCodeKeys.E003;
    }

    /**
     * 从系统中删除服务节点
     * @param nodeId
     * @return
     */
    public int unregisterNode(int nodeId) {

        //如果服务节点编号未被注册, 返回E007:服务节点不存在。
        if(!nodes.contains(nodeId))
        {
            return ReturnCodeKeys.E007;
        }
        //清除成功，返回E006:服务节点清除成功。
        nodes.remove(new Integer(nodeId));

        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {

        //如果任务编号小于等于0, 返回E009:任务编号非法。
        if(taskId<=0)
        {
            return ReturnCodeKeys.E009;
        }
        //如果相同任务编号任务已经被添加, 返回E010:任务已添加。
        if(tasks.contains(taskId)) {
            return ReturnCodeKeys.E010;
        }
        //添加成功，返回E008任务添加成功。
        tasks.add(taskId);

        taskMap.put(taskId,consumption);

        Collections.sort(tasks,comparatorByTime);

        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {

        // 如果指定编号的任务未被添加, 返回E012:任务不存在。
        if(!tasks.contains(taskId))
        {
            return ReturnCodeKeys.E012;
        }
        //删除成功，返回E011:任务删除成功。
        tasks.remove(new Integer(taskId));
        taskMap.remove(new Integer(taskId));
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {

        if(threshold<0)
        {
            return ReturnCodeKeys.E002;
        }


        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }


    private int countTasks(List<TaskInfo> taskInfos){
        int result = 0;
        for(TaskInfo taskInfo:taskInfos){
            result+=taskMap.get(taskInfo.getTaskId());
        }
        return result;
    }
}
