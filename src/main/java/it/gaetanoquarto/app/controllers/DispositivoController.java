package it.gaetanoquarto.app.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.gaetanoquarto.app.entities.Dispositivo;
import it.gaetanoquarto.app.services.DispositivoService;


@RestController
@RequestMapping("/")
public class DispositivoController {

	@Autowired
	private DispositivoService ds;
	
	@GetMapping("dispositivi")
	public ResponseEntity<List<Dispositivo>> getAll() {
		List<Dispositivo> dispositivi = ds.getAll();
		
		if(dispositivi.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(dispositivi, HttpStatus.OK);
	}
	
	@GetMapping("dispositivi/{id}")
	public ResponseEntity<Object> getById(@PathVariable Integer id) {
		
		Optional<Dispositivo> dispositivoObj = ds.getById(id);
		
		ResponseEntity<Object> check = checkExists(dispositivoObj);
		if(check != null) return check;
		
		return new ResponseEntity<>(dispositivoObj.get(), HttpStatus.OK);
	}
	
	@GetMapping("dispositivi_page")
	public ResponseEntity<Object> getAll_page(Pageable pageable) {
		Page<Dispositivo> dispositivi = ds.getAll_page(pageable);
		
		if(dispositivi.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(dispositivi, HttpStatus.OK);
	}
	
	@PostMapping("dispositivi")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> create(@RequestBody Dispositivo d) {
		Dispositivo dispositivi = ds.save(d);
		
		return new ResponseEntity<Object>(dispositivi, HttpStatus.CREATED);
	}
	
	@PutMapping("dispositivi/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Dispositivo _dispositivo) {
		
		Optional<Dispositivo> dispositivoObj = ds.getById(id);
		
		ResponseEntity<Object> check = checkExists(dispositivoObj);
		if(check != null) return check;
		
		Dispositivo dispositivo = dispositivoObj.get();
		dispositivo.setTipoDispositivo( _dispositivo.getTipoDispositivo() );
		dispositivo.setStato( _dispositivo.getStato() );
		ds.save(dispositivo);
		
		
		return new ResponseEntity<Object>(dispositivo, HttpStatus.CREATED);
	}
	
	@DeleteMapping("dispositivi/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable Integer id) {
		
		Optional<Dispositivo> dispositivoObj = ds.getById(id);
		ResponseEntity<Object> check = checkExists(dispositivoObj);
		if(check != null) return check;
		
		ds.delete(dispositivoObj.get());
		return new ResponseEntity<>(
			String.format("Dispositivo con l'id %d Ã¨ stato eliminato!", id), HttpStatus.OK	
		);
	}
	
	private ResponseEntity<Object> checkExists(Optional<Dispositivo> obj) {
		if( !obj.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return null;
	}
}
