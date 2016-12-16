package com.leoni.data.models;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: hrmi1005
 * Date: 19.8.2015
 * Time: 14:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "step_sequence")
public class WorkplaceStepsSequence implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "sequence")
    private Integer sequence;
    @OneToOne
    @JoinColumn(name = "wp_id")
    private Workplace workplace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    @Override
    public int compareTo(Object o) {

            WorkplaceStepsSequence wss = (WorkplaceStepsSequence) o;
            if(this.getWorkplace().getSide().trim().equals(wss.getWorkplace().getSide().trim())){
                return this.getSequence().compareTo(wss.getSequence());} // null is before other strings
            else // this.member != null
                return this.getWorkplace().getSide().trim().compareTo(wss.getWorkplace().getSide().trim());

    }
}
