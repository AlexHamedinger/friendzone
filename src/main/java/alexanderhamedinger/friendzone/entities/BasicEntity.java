package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@MappedSuperclass
public class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "creationdate")
    private GregorianCalendar creationDate;

    //getter and setter
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public GregorianCalendar getCreationDate() {
        return creationDate;
    }
    public String getCreationDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        String date = dateFormat.format(this.getCreationDate().getTime());
        return date;
    }
    public String getCreationDateDifference() {
        String dateDifference = "";
        GregorianCalendar now = new GregorianCalendar();
        long difference = now.getTimeInMillis() - creationDate.getTimeInMillis();
        int days = (int)(difference / (1000 * 60 * 60 * 24));
        int hours = (int)(difference / (1000 * 60 * 60) % 24);
        int minutes = (int)(difference / (1000 * 60) % 60);
//        int seconds = (int)(difference / 1000 % 60);
//        int millis = (int)(difference % 1000);

        if(days < 1) {
            if(hours < 1) {
                if(minutes < 5) {
                    dateDifference = "gerade eben";
                } else {
                    dateDifference = "vor " + minutes + " Minuten";
                }
            } else {
                dateDifference = "vor " + hours + " Stunden";
            }
        } else {
            dateDifference = "vor " + days + " Tagen";
        }

        return dateDifference;
    }
    public void setCreationDate(GregorianCalendar creationDate) {
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
