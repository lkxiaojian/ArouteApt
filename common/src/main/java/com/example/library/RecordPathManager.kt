package com.example.library

import com.example.library.bean.PathBean

object RecordPathManager {
    private val gropMap=HashMap<String,ArrayList<PathBean>>()
    /**
     * 存储 Class
     */
    fun joinGroup(groupName:String,pathName:String,clazz: Class<*>){
        var list = gropMap[groupName]
        if(list.isNullOrEmpty()){
            list= arrayListOf()
            list.add(PathBean(groupName,pathName,clazz))
            gropMap[groupName]=list
        }else{
            list.forEach {
                if(it.pathName!=pathName){
                    list.add(PathBean(groupName,pathName,clazz))
                    gropMap[groupName]=list
                }
            }
        }
    }

    /**
     * 根据 groupName pathName 获取Class
     */
    fun getTargetClass(groupName:String,pathName:String):Class<*>?{
        val arrayList = gropMap[groupName]
        if(arrayList.isNullOrEmpty()){
            return null
        }
        arrayList.forEach {
            if(it.pathName.equals(pathName,false)){
                return it.classNa
            }
        }
        return null
    }
}