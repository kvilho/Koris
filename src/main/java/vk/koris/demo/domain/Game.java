package vk.koris.demo.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Integer mins;
    private Integer points;
    private Integer rebounds;
    private Integer assists;
    private Integer blocks;
    private Integer steals;
    private Integer fgm;
    private Integer fga;
    private LocalDate date;
    private Double fgp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "playerid")
    private Player player;

    private String opponent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMins() {
        return mins;
    }

    public void setMins(Integer mins) {
        this.mins = mins;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getRebounds() {
        return rebounds;
    }

    public void setRebounds(Integer rebounds) {
        this.rebounds = rebounds;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getBlocks() {
        return blocks;
    }

    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    public Integer getSteals() {
        return steals;
    }

    public void setSteals(Integer steals) {
        this.steals = steals;
    }

    public Integer getFgm() {
        return fgm;
    }

    public void setFgm(Integer fgm) {
        this.fgm = fgm;
        updateFgp();
    }

    public Integer getFga() {
        return fga;
    }

    public void setFga(Integer fga) {
        this.fga = fga;
        updateFgp();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Game() {
    }

    public Game(Long id, Integer mins, Integer points, Integer rebounds, Integer assists, Integer blocks,
            Integer steals, Integer fgm, Integer fga, Player player, String opponent) {
        this.id = id;
        this.mins = mins;
        this.points = points;
        this.rebounds = rebounds;
        this.assists = assists;
        this.blocks = blocks;
        this.steals = steals;
        this.fgm = fgm;
        this.fga = fga;
        this.player = player;
        this.opponent = opponent;
        this.updateFgp();
    }


    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    

    

    @Override
    public String toString() {
        return "Game [id=" + id + ", mins=" + mins + ", points=" + points + ", rebounds=" + rebounds + ", assists="
                + assists + ", blocks=" + blocks + ", steals=" + steals + ", fgm=" + fgm + ", fga=" + fga + ", date="
                + date + ", fgp=" + fgp + ", player=" + player + ", opponent=" + opponent + "]";
    }

    public Double getFgp() {
        return fgp;
    }

    private void updateFgp() {
        if (this.fgm != null && this.fga != null && this.fga > 0) {
            this.fgp = (double) this.fgm / this.fga * 100;
            BigDecimal roundFgp = new BigDecimal(this.fgp).setScale(1, RoundingMode.HALF_UP);
            this.fgp = roundFgp.doubleValue();
        } else {
            this.fgp = 0.0;
        }
    }

    public void setFgp(Double fgp) {
        this.fgp = fgp;
    }
    

    

}
