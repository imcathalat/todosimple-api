package com.pandora.todosimple.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.Override;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty.Access;

import com.fasterxml.jackson.annotation.JsonProperty;



// entidade pode ser tratada com persistência de dados (algo que eu gostaria de fazer um CRUD)
@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {}

    public interface UpdateUser {}
    
    public static final String TABLE_NAME = "user";

    // tipo de classe como Integer permitem valores nulos, tipos int não permitem, portanto não é o mais recomendado

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name="username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUser.class) // n pode ser um valor null no CU
    @NotEmpty(groups = CreateUser.class) //n pode ser uma string vazia "" no CU
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;
    // não é aconselhavel deixar atualizar o identificador de usuario, portanto, na hora de atualizar o usuário é permitido que username seja nulo

    @JsonProperty(access = Access.WRITE_ONLY) // isso garante que a senhe não é retornada pro cliente da API
    @Column(name="password", length = 60, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 60)
    private String password;

    @OneToMany(mappedBy = "user") //um usuário pode ter várias classes
    // qual variavel mapeia de quem que é a task no model Task? a variavel us3r
    private List<Task> tasks = new ArrayList<Task> ();

    public User() {
        // isso é um construtor vazio
    }

    public User(Long id, String username, String password){
        // construtor com todos os campos
        // quando alguém mandar criar um usuário, vai cair nesse construtor com os campos

        this.id = id;
        this.username = username;
        this.password = password;
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;

        // setters usam void, pois são métodos sem retorno
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTask(){
        return this.tasks;
    }

    public void setTasks(List<Task> tasks){
        this.tasks = tasks;
    }

    // hashCode and Equals: verificação do Objeto para validação dos dados

    @Override //anotação que indica que esse método esta substituindo um método da superclasse (qual a super classe nesse exemplo?)
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj; // casting
        if(this.id == null)
            if (other.id != null)
                return false;
            else if (!this.id.equals(other.id))
                return false;
        return Objects.equals(this.id, other.id) && Objects.equals(this.username, other.username) && Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }

}
