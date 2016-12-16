package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 12.1.2015
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "clip_status", schema = "public", catalog = "")
@Entity
public class ClipStatus {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column (name = "id")
        private Integer id;
        @Column (name = "id_switch")
        private String idSwitch;
        @Column (name = "id_clip")
        private String idClip;
        @Column (name = "status")
        private Boolean status;
        @Column (name = "board_typ")
        private String boardTyp;
        @Column (name = "board_id")
        private String boardId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdSwitch() {
        return idSwitch;
    }

    public void setIdSwitch(String idSwitch) {
        this.idSwitch = idSwitch;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getIdClip() {
        return idClip;
    }

    public void setIdClip(String idClip) {
        this.idClip = idClip;
    }

    public String getBoardTyp() {
        return boardTyp;
    }

    public void setBoardTyp(String boardTyp) {
        this.boardTyp = boardTyp;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
