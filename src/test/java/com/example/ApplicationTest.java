package com.example;

import com.example.pokemon.Pokemon;
import com.example.pokemon.PokemonCreationForm;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class ApplicationTest {

    private static final Logger log = LoggerFactory.getLogger(ApplicationTest.class);

    private final Integer pokemonEntriesInDB = 4;

    @BeforeEach
    void setUp() {
        RestAssured.port = 5000;
    }

    @Test
    void should_return_list_of_pokemon() {

        List<Pokemon> list = when()
                .get("/pokemon")
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<Pokemon>>() {
                });

        assertThat(list).hasOnlyElementsOfType(Pokemon.class);
        assertThat(list).hasSize(pokemonEntriesInDB);
    }

    @Test
    void should_return_pokemon_with_id_one() {
        Integer idOfPokemonToBeFetched = 1;

        Pokemon pokemon = when()
                .get("/pokemon/" + idOfPokemonToBeFetched)
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(pokemon.getId()).isEqualTo(1);
        assertThat(pokemon.getName()).isEqualTo("Pikachu");
        assertThat(pokemon.getPower().getName()).isEqualTo("Grass");
    }

    @Test
    void should_have_error_message_no_pokemon_found() {
        Integer idOfPokemonToBeFetched = 10;

        ErrorMessage eMessage = when()
                .get("/pokemon/" + idOfPokemonToBeFetched)
                .then()
                .statusCode(400)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(eMessage.getMessage()).isEqualTo("Pokemon not found");
    }

    @Test
    void should_create_new_pokemon() {
        PokemonCreationForm pokemonCreationForm = new PokemonCreationForm(
                ("Bulbasaur-" + Math.round(Math.random() * 1000)), 2, "/bulbasaur.png");

        Pokemon pokemon = given()
                .body(pokemonCreationForm).contentType(ContentType.JSON)
                .when()
                .post("/pokemon")
                .then()
                .statusCode(201)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(pokemon.getName()).isEqualTo(pokemonCreationForm.getName());
        assertThat(pokemon.getPower().getName()).isEqualTo("Water");
        assertThat(pokemon.getImgUrl()).isEqualTo("/bulbasaur.png");
    }

    @Test
    void should_have_error_message_pokemon_with_given_name_already_exists() {
        PokemonCreationForm pokemonCreationForm = new PokemonCreationForm(
                ("Bulbasaur-" + 2), 3, "/bulbasaur.png");

        ErrorMessage errorMessage = given()
                .body(pokemonCreationForm).contentType(ContentType.JSON)
                .when()
                .post("/pokemon")
                .then()
                .statusCode(400)
                .extract()
                .as(new TypeRef<>() {
                });

        assertThat(errorMessage.getMessage())
                .isEqualTo("Pokemon with name: "
                        + pokemonCreationForm.getName() + " Already Exists");
    }

    private static class ErrorMessage {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}