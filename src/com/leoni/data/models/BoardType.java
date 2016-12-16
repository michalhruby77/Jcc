package com.leoni.data.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.8.2015
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "board_type")
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    private BandType band;
    @ManyToMany(fetch = FetchType.EAGER,/* cascade = CascadeType.ALL*/cascade =  CascadeType.MERGE)
    @JoinTable(name = "step_board",
            joinColumns = @JoinColumn(name = "board_type_id") ,
            inverseJoinColumns =  @JoinColumn(name = "step_seq_id") )
    private List<WorkplaceStepsSequence> workplaceStepsSequenceList;

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

    public BandType getBand() {
        return band;
    }

    public void setBand(BandType band) {
        this.band = band;
    }

    public List<WorkplaceStepsSequence> getWorkplaceStepsSequenceList() {
        return workplaceStepsSequenceList;
    }

    public void setWorkplaceStepsSequenceList(List<WorkplaceStepsSequence> workplaceStepsSequenceList) {
        this.workplaceStepsSequenceList = workplaceStepsSequenceList;
    }
}
