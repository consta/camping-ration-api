package com.kanstantin.camping.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.kanstantin.camping.domain.TripMealDao;
import com.kanstantin.camping.model.MyResponse;
import com.kanstantin.camping.model.TripMealDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class TripMealApiController {

    @Autowired
    private TripMealDao tripMealDao;

    // GET: Retrieve all meals for a specific trip day
    @RequestMapping(value = "/trips/{tripId}/days/{dayId}/meals", method = RequestMethod.GET)
    public ResponseEntity<MyResponse<List<TripMealDTO>>> getMeals(@PathVariable(name = "tripId") Integer tripId,
                                                                  @PathVariable(name = "dayId") Integer dayId,
                                                                  @RequestParam(name = "readDescendants", required = false) Boolean readDescendants) {
        MyResponse<List<TripMealDTO>> response = new MyResponse<>(1000, "success", tripMealDao.readTripMeals(dayId, readDescendants));
        return ResponseEntity.ok(response);
    }

    // POST: Create a new meal for a specific trip day
    @RequestMapping(value = "/trips/{tripId}/days/{dayId}/meals", method = RequestMethod.POST)
    public ResponseEntity<MyResponse<TripMealDTO>> addMeal(@PathVariable(name = "tripId") Integer tripId,
                                                           @PathVariable(name = "dayId") Integer dayId,
                                                           @RequestBody TripMealDTO dto) {
        MyResponse<TripMealDTO> response = new MyResponse<>(1000, "success", tripMealDao.save(dayId, dto));
        return ResponseEntity.ok(response);
    }

    // PUT: Update a meal for a specific trip day
    @RequestMapping(value = "/trips/{tripId}/days/{dayId}/meals/{mealId}", method = RequestMethod.PUT)
    public ResponseEntity<MyResponse<TripMealDTO>> updateMeal(@PathVariable(name = "tripId") Integer tripId,
                                                              @PathVariable(name = "dayId") Integer dayId,
                                                              @PathVariable(name = "mealId") Integer mealId,
                                                              @RequestBody TripMealDTO dto) {
        MyResponse<TripMealDTO> response = new MyResponse<>(1000, "success", tripMealDao.save(dayId, dto));
        return ResponseEntity.ok(response);
    }

    // DELETE: Delete a meal for a specific trip day
    @RequestMapping(value = "/trips/{tripId}/days/{dayId}/meals/{mealId}", method = RequestMethod.DELETE)
    public ResponseEntity<MyResponse<Boolean>> deleteMeal(@PathVariable(name = "tripId") Integer tripId,
                                                           @PathVariable(name = "dayId") Integer dayId,
                                                           @PathVariable(name = "mealId") Integer mealId) {
        boolean isDeleted = tripMealDao.delete(mealId);
        return ResponseEntity.ok(new MyResponse<>(1000, "success", isDeleted));
    }
}
