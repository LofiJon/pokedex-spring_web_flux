package com.dio.webflux.pokedex;

import com.dio.webflux.pokedex.model.Pokemon;
import com.dio.webflux.pokedex.repository.PokemonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ReactiveMongoOperations operations, PokemonRepository repository ) {
		return args -> {
			Flux<Pokemon> pokemonFlux = Flux.just(
					new Pokemon(null, "Blastoise", "marisco", "Torrente", 10.35),
					new Pokemon(null, "Caterpie", "Minhoca", "poeira de excuto", 4.25),
					new Pokemon(null, "Bulbassauro", "semente", "Grandeza", 10.35)
			 	).flatMap(repository::save);
			pokemonFlux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
		};
	}

}
