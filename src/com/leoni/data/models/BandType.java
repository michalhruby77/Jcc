package com.leoni.data.models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.8.2015
 * Time: 13:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "band_type")
public class BandType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "name")
    private String name;
    @OneToMany(orphanRemoval=true,cascade = CascadeType.ALL, mappedBy = "band")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<BoardType> boardTypeList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BoardType> getBoardTypeList() {
        return boardTypeList;
    }

    public void setBoardTypeList(List<BoardType> boardTypeList) {
        this.boardTypeList = boardTypeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BandType)) return false;

        BandType bandType = (BandType) o;

        if (!id.equals(bandType.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
