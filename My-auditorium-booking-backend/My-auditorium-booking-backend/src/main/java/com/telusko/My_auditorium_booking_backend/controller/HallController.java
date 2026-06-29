package com.telusko.My_auditorium_booking_backend.controller;

import com.telusko.My_auditorium_booking_backend.dto.request.HallRequestDto;
import com.telusko.My_auditorium_booking_backend.dto.response.HallResponseDto;
import com.telusko.My_auditorium_booking_backend.service.HallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/halls")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class HallController {

    private final HallService hallService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallResponseDto> createHall(
            @Valid @RequestBody HallRequestDto request,
            Authentication authentication
    ){
        String email = authentication.getName();
        return new ResponseEntity<>(hallService.createHall(request,email), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<HallResponseDto>> getAllHalls(
            @PageableDefault(size = 10, sort = "name")Pageable pageable,
            Authentication authentication
            ){
        String email = authentication.getName();

        return ResponseEntity.ok(hallService.getAllHalls(pageable,email));
    }

    //UPDATE HALL
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallResponseDto> updateHall(
          @PathVariable Long id,
          @Valid @RequestBody HallRequestDto request,
          Authentication authentication
    ){
        String email = authentication.getName();

        return ResponseEntity.ok(hallService.updateHall(id,request,email));
    }

    //DELETE HALL
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteHall(
            @PathVariable Long id,
            Authentication authentication
    ){
        String email = authentication.getName();
        hallService.deleteHall(id,email);
        return ResponseEntity.ok("Hall deleted Successfully");
    }

    //GET AVAILABLE (ALL LOGGED USERS)
    @GetMapping("/available")
    public ResponseEntity<Page<HallResponseDto>> getEnabledHalls(
            @PageableDefault(size = 10,sort = "name") Pageable pageable
    ){
        return ResponseEntity.ok(hallService.getAllEnabledHalls(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallResponseDto> getHall(@PathVariable Long id){
        return ResponseEntity.ok(hallService.getHallById(id));
    }

}
