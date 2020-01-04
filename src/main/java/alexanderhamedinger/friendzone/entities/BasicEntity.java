package alexanderhamedinger.friendzone.entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Objects;

@MappedSuperclass
public class BasicEntity {
//Mapped Superclass: Attribute und Methoden werden an alle anderen Entitäten vererbt

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
    public void setCreationDate(GregorianCalendar creationDate) {
        this.creationDate = creationDate;
    }

    //methoden
    //gibt das Datum als lesbaren String zurück
    public String getCreationDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy kk:mm:ss");
        String date = dateFormat.format(this.getCreationDate().getTime());
        return date;
    }
    public String getCreationDateAsStringShort() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(this.getCreationDate().getTime());
        return date;
    }
    //gibt die Differenz vom jetzigen Zeitpunkt zum Erstelldatum (in Tagen, Stunden oder Minuten) zurück
    public String getCreationDateDifference() {
        String dateDifference = "";
        GregorianCalendar now = new GregorianCalendar();
        long difference = now.getTimeInMillis() - creationDate.getTimeInMillis();
        int days = (int)(difference / (1000 * 60 * 60 * 24));
        int hours = (int)(difference / (1000 * 60 * 60) % 24);
        int minutes = (int)(difference / (1000 * 60) % 60);

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
        Long thisId = new Long(id);
        if(thisId.equals(null)) {
            return 0;
        } else {
            return (int)id;
        }
    }
}
