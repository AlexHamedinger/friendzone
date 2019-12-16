package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "creationdate")
    private Date creationDate;

    //getter and setter
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    //Override
    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        BasicEntity other = (BasicEntity) o;
        if(!Objects.equals(id, other.id)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        if(id == 0L) {
            return 0;
        } else {
            return (int)id;
        }
    }
}
