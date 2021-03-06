package com.huwang.traffic_portal.service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huwang.traffic_portal.dao.LoadDao;
import com.huwang.traffic_portal.dao.PointDao;
import com.huwang.traffic_portal.entity.LoadEntity;
import com.huwang.traffic_portal.entity.Point;
import com.huwang.traffic_portal.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoadService {

    private static final Logger log = LoggerFactory.getLogger(LoadService.class);
    @Autowired
    private LoadDao dao;
    @Autowired
    private PointDao pointDao;

    public List<LoadEntity> getLoads(double lat,double lng,int unit)
    {
        List<LoadEntity> response=dao.getLoads(lat,lng,unit);
        List<LoadEntity> loads=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mapJakcson = mapper.writeValueAsString(response);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, LoadEntity.class);
            loads = mapper.readValue(mapJakcson, javaType);
        }catch (Exception e){}
        for (LoadEntity load : loads) {
            List<Point> points = pointDao.getPoints(load.getId(), CommonUtils.LOADTYPE);
            load.setPoints(points);
        }
        return loads;
    }

    public List<LoadEntity> searchLoad(double lat,double lng,int unit,String str)
    {
        List<LoadEntity> response=dao.searchData(lat,lng,unit,str);
        List<LoadEntity> loads=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mapJakcson = mapper.writeValueAsString(response);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, LoadEntity.class);
            loads = mapper.readValue(mapJakcson, javaType);
        }catch (Exception e){}
        for (LoadEntity load : loads) {
            List<Point> points = pointDao.getPoints(load.getId(), CommonUtils.LOADTYPE);
            load.setPoints(points);
        }
        return loads;
    }

    public List<LoadEntity> searchAgencyLoads(double lat,double lng,int unit,Integer id)
    {
        List<LoadEntity> response=dao.searchDataAgency(lat,lng,unit,id);
        List<LoadEntity> loads=new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String mapJakcson = mapper.writeValueAsString(response);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, LoadEntity.class);
            loads = mapper.readValue(mapJakcson, javaType);
        }catch (Exception e){}
        for (LoadEntity load : loads) {
            List<Point> points = pointDao.getPoints(load.getId(), CommonUtils.LOADTYPE);
            load.setPoints(points);
        }
        return loads;
    }

    public void addPoint(double lat,double lng,int pid)
    {
        Point point=new Point();
        point.setLat(lat);
        point.setLng(lng);
        point.setParentId(pid);
        point.setType(CommonUtils.MAINTENANCETYPE);
        pointDao.saveEntity(point);
    }
}
