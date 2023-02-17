package it.gaetanoquarto.app;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import it.gaetanoquarto.app.config.Beans;
import it.gaetanoquarto.app.entities.Dispositivo;
import it.gaetanoquarto.app.entities.Ruolo;
import it.gaetanoquarto.app.entities.StatoDispositivo;
import it.gaetanoquarto.app.entities.TipoDispositivo;
import it.gaetanoquarto.app.entities.TipoRuolo;
import it.gaetanoquarto.app.entities.Utente;
import it.gaetanoquarto.app.services.DispositivoService;
import it.gaetanoquarto.app.services.RuoloService;
import it.gaetanoquarto.app.services.UtenteService;


@SpringBootApplication
public class AppApplication implements CommandLineRunner{

	public static void main(String[] args) {
		try {
			System.out.println("sono nel main");
			SpringApplication.run(AppApplication.class, args);

		} catch(Exception e) {
			e.getStackTrace();
		}
	}
	
	@Autowired
	private DispositivoService ds;
	@Autowired
	private UtenteService us;
	@Autowired
	private RuoloService rs;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("sono nel run");
		//popolaDb();
		getRuoloFromUserById(1);
	}

	private void popolaDb() {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Beans.class);
		
		Utente u1 = (Utente)ctx.getBean("utente", "Gaetano", "Quarto", "gaetano@mail.com", "ciao", "gaithan");
		Utente u2 = (Utente)ctx.getBean("utente", "admin", "admin", "admin@admin.com", "admin", "admin");
		
		Ruolo r1 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ROLE_ADMIN);
		Ruolo r2 = (Ruolo)ctx.getBean("ruolo", TipoRuolo.ROLE_USER);
		
		u1.setRuoli(new HashSet<>() {{
			add(r2);
		}});
		u2.setRuoli(new HashSet<>() {{
			add(r1);
		}});
		
		Dispositivo d1 = (Dispositivo)ctx.getBean("dispositivo", TipoDispositivo.SMARTPHONE, StatoDispositivo.ASSEGNATO);
		Dispositivo d2 = (Dispositivo)ctx.getBean("dispositivo", TipoDispositivo.LAPTOP, StatoDispositivo.DISPONIBILE);
		Dispositivo d3 = (Dispositivo)ctx.getBean("dispositivo", TipoDispositivo.TABLET, StatoDispositivo.IN_MANUTENZIONE);
		
		u2.setDispositivi(new HashSet<>() {{
			add(d1);
		}});
		
		rs.save(r1);
		rs.save(r2);
		
		ds.save(d1);
		ds.save(d2);
		ds.save(d3);
		
		us.save(u1);
		us.save(u2);
		
		System.out.println("DB POPOLATO!");
		((AnnotationConfigApplicationContext)ctx).close();

	}
	
	private void getRuoloFromUserById(int id) {
		Optional<Utente> authUserObj = us.getById(id);
		Utente authUser = authUserObj.get();
		String ruolo = "USER";
		Set<Ruolo> ruoli = authUser.getRuoli();
		
		for( Ruolo r : ruoli ) {
			if( r.getTipoRuolo().toString().contains("ADMIN") ) {
				ruolo = "ADMIN";
				break;
			}
		}
		
		System.out.println(ruolo);
	}
}
