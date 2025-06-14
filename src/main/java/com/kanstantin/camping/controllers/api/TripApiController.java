package com.kanstantin.camping.controllers.api;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanstantin.camping.domain.TripDao;
import com.kanstantin.camping.domain.TripDayDao;
import com.kanstantin.camping.model.TripDTO;
import com.kanstantin.camping.model.MyResponse;
import com.kanstantin.camping.model.TripDayDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class TripApiController {
    @Autowired
    private TripDao tripDao;
    @Autowired
    private TripDayDao tripDayDao;

    @RequestMapping(value = "/trips", method = RequestMethod.GET)
    public ResponseEntity<MyResponse<List<TripDTO>>> getTrips() {
        MyResponse<List<TripDTO>> response = new MyResponse<>(1000, "success", tripDao.readAll());
        log.info("retrieved all trips, {}", response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips", method = RequestMethod.POST)
    public ResponseEntity<MyResponse<TripDTO>> addTrip(@RequestBody TripDTO dto) {
        MyResponse<TripDTO> response = new MyResponse<>(1000, "success", tripDao.save(dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse<TripDTO>> getTripById(@PathVariable(name = "id") Integer id) {
        MyResponse<TripDTO> response = new MyResponse<>(1000, "success", tripDao.readById(id));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MyResponse<TripDTO>> updateTrip(@PathVariable(name = "id") Integer id, @RequestBody TripDTO updatedDto) {
        updatedDto.setId(id); // Setting the ID in the DTO
        MyResponse<TripDTO> response = new MyResponse<>(1000, "success", tripDao.save(updatedDto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{tripId}/days", method = RequestMethod.GET)
    public ResponseEntity<MyResponse<List<TripDayDTO>>> getTripDaysByTripId(@PathVariable(name = "tripId") Integer tripId,
                                                          @RequestParam(name = "readDescendants", required = false) Boolean readDescendants) {
        MyResponse<List<TripDayDTO>> response = new MyResponse<>(1000, "success", tripDayDao.readTripDays(tripId, readDescendants));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{tripId}/days/{dayId}", method = RequestMethod.GET)
    public ResponseEntity<MyResponse<TripDayDTO>> getTripDayByDayId(@PathVariable(name = "tripId") Integer tripId,
                                                          @PathVariable(name = "dayId") Integer dayId,
                                                          @RequestParam(name = "readDescendants", required = false) Boolean readDescendants) {
        MyResponse<TripDayDTO> response = new MyResponse<>(1000, "success", tripDayDao.readById(dayId, readDescendants));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{tripId}/days", method = RequestMethod.POST)
    public ResponseEntity<MyResponse<TripDayDTO>> addTripDay(@PathVariable(name = "tripId") Integer tripId,
                                                 @RequestBody TripDayDTO dto) {
        MyResponse<TripDayDTO> response = new MyResponse<>(1000, "success", tripDayDao.save(tripId, dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{tripId}/days/{dayId}", method = RequestMethod.PUT)
    public ResponseEntity<MyResponse<TripDayDTO>> updateTripDay(@PathVariable(name = "tripId") Integer tripId,
                                                    @PathVariable(name = "dayId") Integer dayId,
                                                 @RequestBody TripDayDTO dto) {
        MyResponse<TripDayDTO> response = new MyResponse<>(1000, "success", tripDayDao.save(tripId, dto));
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/trips/{tripId}/days/{dayId}", method = RequestMethod.DELETE)
    public ResponseEntity<MyResponse<Boolean>> deleteTripDay(@PathVariable(name = "tripId") Integer tripId,
                                                    @PathVariable(name = "dayId") Integer dayId) {
        MyResponse<Boolean> response = new MyResponse<>(1000, "success", tripDayDao.delete(dayId));
        return ResponseEntity.ok(response);
    }

}
