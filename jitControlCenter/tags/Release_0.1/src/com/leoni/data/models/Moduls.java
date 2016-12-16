package com.leoni.data.models;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table (name = "moduls")
public class Moduls
    {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SACH_NR_BEST = "sachNrBest";
    public static final String COLUMN_SACH_NR_LIEFERANT = "sachNrLieferant";
    public static final String COLUMN_PROD_GRUPPE = "prodGruppe";
    public static final String COLUMN_KABELSATZ_KZ = "kabelsatzKz";
    public static final String COLUMN_AUSFUEHRUNG = "ausfuehrung";
    public static final String COLUMN_GRUND = "grund";
    public static final String COLUMN_BLOCK = "block";
    public static final String COLUMN_CREATE_PERSON = "createPerson";
    public static final String COLUMN_CREATE_TIME = "createTime";
    public static final String COLUMN_COMMENTARY = "commentary";

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;
    @Column (name = "sach_nr_best")
    private String sachNrBest;
    @Column (name = "sach_nr_lieferant")
    private String sachNrLieferant;
    @Column (name = "prod_gruppe")
    private String prodGruppe;
    @Column (name = "kabelsatz_kz")
    private String kabelsatzKz;
    @Column (name = "ausfuehrung")
    private String ausfuehrung;
    @Column (name = "grund")
    private Boolean grund;
    @Column (name = "block")
    private Boolean block;
    @Column (name = "create_person")
    private String createPerson;
    @Column (name = "create_time")
    private Timestamp createTime;
    @Column (name = "commentary")
    private String commentary;

    public Integer getId()
        {
        return id;
        }

    public void setId(Integer id)
        {
        this.id = id;
        }

    public String getSachNrBest()
        {
        return sachNrBest;
        }

    public void setSachNrBest(String sachNrBest)
        {
        this.sachNrBest = sachNrBest;
        }

    public String getSachNrLieferant()
        {
        return sachNrLieferant;
        }

    public void setSachNrLieferant(String sachNrLieferant)
        {
        this.sachNrLieferant = sachNrLieferant;
        }

    public String getProdGruppe()
        {
        return prodGruppe;
        }

    public void setProdGruppe(String prodGruppe)
        {
        this.prodGruppe = prodGruppe;
        }

    public String getKabelsatzKz()
        {
        return kabelsatzKz;
        }

    public void setKabelsatzKz(String kabelsatzKz)
        {
        this.kabelsatzKz = kabelsatzKz;
        }

    public String getAusfuehrung()
        {
        return ausfuehrung;
        }

    public void setAusfuehrung(String ausfuehrung)
        {
        this.ausfuehrung = ausfuehrung;
        }

    public Boolean isGrund()
        {
        return grund;
        }

    public Boolean getGrund()
        {
        return grund;
        }

    public Boolean getBlock()
        {
        return block;
        }

    public void setGrund(Boolean grund)
        {
        this.grund = grund;
        }

    public Boolean isBlock()
        {
        return block;
        }

    public void setBlock(Boolean block)
        {
        this.block = block;
        }

    public Timestamp getCreateTime()
        {
        return createTime;
        }

    public void setCreateTime(Timestamp createTime)
        {
        this.createTime = createTime;
        }

    public String getCreatePerson()
        {
        return createPerson;
        }

    public void setCreatePerson(String createPerson)
        {
        this.createPerson = createPerson;
        }

    public String getCommentary()
        {
        return commentary;
        }

    public void setCommentary(String commentary)
        {
        this.commentary = commentary;
        }

    @Override
    public boolean equals(Object o)
        {
        if (this == o)
            {
            return true;
            }
        if (o == null || getClass() != o.getClass())
            {
            return false;
            }

        Moduls moduls = (Moduls) o;

        if (block != moduls.block)
            {
            return false;
            }
        if (grund != moduls.grund)
            {
            return false;
            }
        if (id != moduls.id)
            {
            return false;
            }
        if (ausfuehrung != null ? !ausfuehrung.equals(moduls.ausfuehrung) : moduls.ausfuehrung != null)
            {
            return false;
            }
        if (commentary != null ? !commentary.equals(moduls.commentary) : moduls.commentary != null)
            {
            return false;
            }
        if (createPerson != null ? !createPerson.equals(moduls.createPerson) : moduls.createPerson != null)
            {
            return false;
            }
        if (createTime != null ? !createTime.equals(moduls.createTime) : moduls.createTime != null)
            {
            return false;
            }
        if (kabelsatzKz != null ? !kabelsatzKz.equals(moduls.kabelsatzKz) : moduls.kabelsatzKz != null)
            {
            return false;
            }
        if (prodGruppe != null ? !prodGruppe.equals(moduls.prodGruppe) : moduls.prodGruppe != null)
            {
            return false;
            }
        if (sachNrBest != null ? !sachNrBest.equals(moduls.sachNrBest) : moduls.sachNrBest != null)
            {
            return false;
            }
        if (sachNrLieferant != null ? !sachNrLieferant.equals(moduls.sachNrLieferant) : moduls.sachNrLieferant != null)
            {
            return false;
            }

        return true;
        }

    @Override
    public int hashCode()
        {
        int result = id != null ? id : 0;
        result = 31 * result + (sachNrBest != null ? sachNrBest.hashCode() : 0);
        result = 31 * result + (sachNrLieferant != null ? sachNrLieferant.hashCode() : 0);
        result = 31 * result + (prodGruppe != null ? prodGruppe.hashCode() : 0);
        result = 31 * result + (kabelsatzKz != null ? kabelsatzKz.hashCode() : 0);
        result = 31 * result + (ausfuehrung != null ? ausfuehrung.hashCode() : 0);
        if (grund != null)
            {
            result = 31 * result + (grund ? 1 : 0);
            }
        if (grund != null)
            {
            result = 31 * result + (block ? 1 : 0);
            }
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (createPerson != null ? createPerson.hashCode() : 0);
        result = 31 * result + (commentary != null ? commentary.hashCode() : 0);
        return result;
        }
    }
