package smilyk.homeacc.model;

import javax.persistence.*;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


@MappedSuperclass
public class BaseEntity {
    /** The Constant LIST_DEFAULT_VALUE. */
    public static final String LIST_DEFAULT_VALUE = "EMPTY";

    /** The id. */
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private long id;

    /** The update date. */
    private Date updateDate;

    /** The create date. */
    private Date createDate;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Gets the update date.
     *
     * @return the update date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the update date.
     *
     * @param updateDate the new update date
     */
    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Gets the creates the date.
     *
     * @return the creates the date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creates the date.
     *
     * @param createDate the new creates the date
     */
    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    /**
     * On persist.
     */
    @PrePersist
    public void onPersist() {
        final DateTime nowDt = new DateTime(DateTimeZone.UTC);
        final Date current = new Date(nowDt.getMillis());
        setCreateDate(current);
        setUpdateDate(current);
    }

    /**
     * On update.
     */
    @PreUpdate
    public void onUpdate() {
        setUpdateDate(new Date(new DateTime(DateTimeZone.UTC).getMillis()));
    }
}
