package it.gaetanoquarto.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.gaetanoquarto.app.entities.Dispositivo;
import it.gaetanoquarto.app.repositories.DispositivoRepository;

@Service
public class DispositivoService {

	@Autowired
	private DispositivoRepository dr;
	
	public Dispositivo save(Dispositivo d) {
		return dr.save(d);
	}
	
	public List<Dispositivo> getAll() {
		return dr.findAll();
	}
	
	public Optional<Dispositivo> getById(int id) {
		return dr.findById(id);
	}
	
	public Page<Dispositivo> getAll_page(Pageable pageable) {
		return dr.findAll(pageable);
	}
	
	public void delete(Dispositivo d) {
		dr.delete(d);
	}
}
