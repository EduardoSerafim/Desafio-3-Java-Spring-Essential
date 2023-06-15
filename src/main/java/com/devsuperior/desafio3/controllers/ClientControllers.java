package com.devsuperior.desafio3.controllers;

import com.devsuperior.desafio3.dto.ClientDTO;
import com.devsuperior.desafio3.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

@RestController
@RequestMapping("/client")
public class ClientControllers {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(@PageableDefault(size = 5) Pageable pageable){
         var page = clientService.findAll(pageable);
         return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
         ClientDTO dto = clientService.findById(id);
         return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO dto){
         dto = clientService.Insert(dto);
         URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                 .buildAndExpand(dto.getId()).toUri();

         return ResponseEntity.created(uri).body(dto);
    }

}
