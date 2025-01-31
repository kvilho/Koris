package vk.koris.demo.domain;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fname;
    private String lname;
    private LocalDate dob;
    private Integer height;
    private Integer weight;

    @Enumerated(EnumType.STRING)
    private Position position;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List <Game> games;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "teamid")
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player() {
    }

    public Player(Long id, String fname, String lname, LocalDate dob, Integer height, Integer weight,
            Position position, Team team) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.position = position;
        this.team = team;
    }

    public String getFullName(){
        return fname + " " + lname;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", fname=" + fname + ", lname=" + lname + ", dob=" + dob + ", height="
                + height + ", weight=" + weight + ", position=" + position +  "]";
    }

    
    

    

}
