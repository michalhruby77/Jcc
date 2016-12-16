package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.1.2015
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "clip_position", schema = "public", catalog = "")
@Entity
public class ClipPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "id_clip")
    private String idClip;
    @Column (name = "board_typ")
    private String boardTyp;
    @Column (name = "x_axis")
    private String xAxis;
    @Column (name = "y_axis")
    private String yAxis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdClip() {
        return idClip;
    }

    public void setIdClip(String idClip) {
        this.idClip = idClip;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setXAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setYAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public String getBoardTyp() {
        return boardTyp;
    }

    public void setBoardTyp(String boardTyp) {
        this.boardTyp = boardTyp;
    }
}
