package com.huwang.traffic_portal;


import com.huwang.traffic_portal.entity.*;
import com.huwang.traffic_portal.service.*;
import com.huwang.traffic_portal.util.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    private static final Logger log = LoggerFactory.getLogger(MapController.class);
    @Autowired
    private LoadService loadService;
    @Autowired
    private UserService userService;
    @Autowired
    private StructureService structureService;
    @Autowired
    private FacilitiesService facilitiesService;
    @Autowired
    private MaintenanceService maintenanceService;
    @Autowired
    private AgencyService agencyService;

    @RequestMapping("/addPoint")
    @ResponseBody
    public String getData(@RequestParam(value = "lat", required = false) Double lat,
                          @RequestParam(value = "lng", required = false) Double lng,
                          @RequestParam(value = "pid", required = false) Integer pid) {
        log.info("[GET point Data]Start get lat=[{}],lng =[{}].", lat, lng, pid);
        loadService.addPoint(lat, lng, pid);
        return "addpoint";
    }

    @RequestMapping("/loadData")
    @ResponseBody
    public ResponseEntity getLoad(@RequestParam(value = "lat", required = false) Double lat,
                                  @RequestParam(value = "lng", required = false) Double lng,
                                  @RequestParam(value = "zoom", required = false) Integer zoom,
                                  @RequestParam(value = "token", required = false) String token) {
        log.info("[GET loadData Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<LoadEntity> loads = loadService.getLoads(lat, lng, zoom);
            responseEntity.setFlag(true);
            responseEntity.setObject(loads);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response loadData Data]Start return loads obj =[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/searchLoad")
    @ResponseBody
    public ResponseEntity searchLoad(@RequestParam(value = "lat", required = false) Double lat,
                                     @RequestParam(value = "lng", required = false) Double lng,
                                     @RequestParam(value = "zoom", required = false) Integer zoom,
                                     @RequestParam(value = "token", required = false) String token,
                                     @RequestParam(value = "searchData", required = false) String searchData) {
        log.info("[GET searchLoad Data]Start get lat=[{}],lng =[{}],zoom=[{}],searchData=[{}],token=[{}].", lat, lng, zoom, searchData, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<LoadEntity> loads = loadService.searchLoad(lat, lng, zoom, searchData);
            responseEntity.setFlag(true);
            responseEntity.setObject(loads);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response searchLoad Data]Start return loads obj =[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/structureData")
    @ResponseBody
    public ResponseEntity getStructure(@RequestParam(value = "lat", required = false) Double lat,
                                       @RequestParam(value = "lng", required = false) Double lng,
                                       @RequestParam(value = "zoom", required = false) Integer zoom,
                                       @RequestParam(value = "token", required = false) String token) {
        log.info("[GET structureData Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<StructureEntity> structures = structureService.getStructures(lat, lng, zoom);
            List<LoadEntity> loads = loadService.getLoads(lat, lng, zoom);
            Map map = new HashMap();
            map.put("structures", structures);
            map.put("loads", loads);
            responseEntity.setFlag(true);
            responseEntity.setObject(map);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response structureData Data]Start return structures obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/searchStructure")
    @ResponseBody
    public ResponseEntity searchStructure(@RequestParam(value = "lat", required = false) Double lat,
                                          @RequestParam(value = "lng", required = false) Double lng,
                                          @RequestParam(value = "zoom", required = false) Integer zoom,
                                          @RequestParam(value = "searchData", required = false) String searchData,
                                          @RequestParam(value = "token", required = false) String token) {
        log.info("[GET searchStructure Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<StructureEntity> structures = structureService.searchStructures(lat, lng, zoom, searchData);
            List<LoadEntity> loads = loadService.getLoads(lat, lng, zoom);
            Map map = new HashMap();
            map.put("structures", structures);
            map.put("loads", loads);
            responseEntity.setFlag(true);
            responseEntity.setObject(map);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response searchStructure Data]Start return structures obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/facilitiesData")
    @ResponseBody
    public ResponseEntity getFacilities(@RequestParam(value = "lat", required = false) Double lat,
                                        @RequestParam(value = "lng", required = false) Double lng,
                                        @RequestParam(value = "zoom", required = false) Integer zoom,
                                        @RequestParam(value = "token", required = false) String token) {
        log.info("[GET facilities Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<FacilitiesEntity> facilities = facilitiesService.getFacilities(lat, lng, zoom);
            List<LoadEntity> loads = loadService.getLoads(lat, lng, zoom);
            Map map = new HashMap();
            map.put("facilities", facilities);
            map.put("loads", loads);
            responseEntity.setFlag(true);
            responseEntity.setObject(map);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response facilities Data]Start return facilities obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/searchFacilities")
    @ResponseBody
    public ResponseEntity searchFacilities(@RequestParam(value = "lat", required = false) Double lat,
                                           @RequestParam(value = "lng", required = false) Double lng,
                                           @RequestParam(value = "zoom", required = false) Integer zoom,
                                           @RequestParam(value = "searchData", required = false) String searchData,
                                           @RequestParam(value = "token", required = false) String token) {
        log.info("[GET searchFacilities Data]Start get lat=[{}],lng =[{}],zoom=[{}],searchData=[{}],token=[{}].", lat, lng, zoom, searchData, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<FacilitiesEntity> facilities = facilitiesService.searchFacilities(lat, lng, zoom, searchData);
            List<LoadEntity> loads = loadService.getLoads(lat, lng, zoom);
            Map map = new HashMap();
            map.put("facilities", facilities);
            map.put("loads", loads);
            responseEntity.setFlag(true);
            responseEntity.setObject(map);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response searchFacilities Data]Start return facilities obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/maintenanceData")
    @ResponseBody
    public ResponseEntity getMaintenance(@RequestParam(value = "lat", required = false) Double lat,
                                         @RequestParam(value = "lng", required = false) Double lng,
                                         @RequestParam(value = "zoom", required = false) Integer zoom,
                                         @RequestParam(value = "token", required = false) String token) {
        log.info("[GET maintenanceData Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<MaintenanceEntity> maintenances = maintenanceService.getMaintenance(lat, lng, zoom);
            responseEntity.setFlag(true);
            responseEntity.setObject(maintenances);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response maintenanceData Data]Start return maintenances obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/searchMaintenance")
    @ResponseBody
    public ResponseEntity searchMaintenance(@RequestParam(value = "lat", required = false) Double lat,
                                            @RequestParam(value = "lng", required = false) Double lng,
                                            @RequestParam(value = "zoom", required = false) Integer zoom,
                                            @RequestParam(value = "searchData", required = false) String searchData,
                                            @RequestParam(value = "token", required = false) String token) {
        log.info("[GET searchMaintenance Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<MaintenanceEntity> maintenances = maintenanceService.searchMaintenance(lat, lng, zoom, searchData);
            responseEntity.setFlag(true);
            responseEntity.setObject(maintenances);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response searchMaintenance Data]Start return maintenances obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/agencyData")
    @ResponseBody
    public ResponseEntity getAgency(@RequestParam(value = "lat", required = false) Double lat,
                                        @RequestParam(value = "lng", required = false) Double lng,
                                        @RequestParam(value = "zoom", required = false) Integer zoom,
                                        @RequestParam(value = "token", required = false) String token) {
        log.info("[GET agencyData Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<AgencyEntity> agencys = agencyService.getAgency(lat, lng, zoom);
            responseEntity.setFlag(true);
            responseEntity.setObject(agencys);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response agencyData Data]Start return agencys obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/searchAgency")
    @ResponseBody
    public ResponseEntity searchAgency(@RequestParam(value = "lat", required = false) Double lat,
                                       @RequestParam(value = "lng", required = false) Double lng,
                                       @RequestParam(value = "zoom", required = false) Integer zoom,
                                       @RequestParam(value = "searchData", required = false) String searchData,
                                       @RequestParam(value = "token", required = false) String token) {
        log.info("[GET searchAgency Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}].", lat, lng, zoom, token);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<AgencyEntity> agencys = agencyService.searchAgency(lat, lng, zoom,searchData);
            responseEntity.setFlag(true);
            responseEntity.setObject(agencys);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response searchAgency Data]Start return agencys obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

    @RequestMapping("/searchAgencyLoads")
    @ResponseBody
    public ResponseEntity searchAgencyLoads(@RequestParam(value = "lat", required = false) Double lat,
                                       @RequestParam(value = "lng", required = false) Double lng,
                                       @RequestParam(value = "zoom", required = false) Integer zoom,
                                       @RequestParam(value = "searchData", required = false) Integer searchData,
                                       @RequestParam(value = "token", required = false) String token) {
        log.info("[GET searchAgencyLoads Data]Start get lat=[{}],lng =[{}],zoom=[{}],token=[{}],searchData=[{}].", lat, lng, zoom, token,searchData);
        ResponseEntity responseEntity = new ResponseEntity();
        if (userService.verifyToken(token)) {
            List<AgencyEntity> agencys = agencyService.getAgency(lat, lng, zoom);
            List<LoadEntity> loads = loadService.searchAgencyLoads(lat, lng, zoom,searchData);
            Map map = new HashMap();
            map.put("agencys", agencys);
            map.put("loads", loads);
            responseEntity.setFlag(true);
            responseEntity.setObject(map);
        } else {
            responseEntity.setFlag(false);
            responseEntity.setObject("token错误或过期");
        }
        log.info("[Response searchAgencyLoads Data]Start return agencys obj=[{}].", responseEntity.getObject());
        return responseEntity;
    }

}
