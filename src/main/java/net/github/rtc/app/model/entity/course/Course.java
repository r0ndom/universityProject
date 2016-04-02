package net.github.rtc.app.model.entity.course;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.Auditable;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.util.annotation.ForExport;
import net.github.rtc.util.annotation.validation.Number;
import net.github.rtc.util.annotation.validation.Validatable;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author Vladislav Pikus
 */
@Entity
@Validatable
public class Course extends AbstractPersistenceObject implements Serializable, Auditable {

    private static final int HASH_CODE_CONSTANT = 31;
    private static final int DESCRIPTION_LENGTH = 255;
    private static final int PRIMARY_LENGTH = 50;
    private static final int DEFAULT_CAPACITY = 10;

    private static final  String COURSE = "Course {";
    private static final  String NAME = ", name=";

    @NotNull
    @Number
    @Min(1)
    @Column
    @ForExport("Capacity")
    private Integer capacity = DEFAULT_CAPACITY;
    @NotEmpty
    @Length(min = 2, max = PRIMARY_LENGTH)
    @Column
    @ForExport("Name")
    private String name;
    @NotEmpty
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CourseTypes",
      joinColumns = @JoinColumn(name = "course_id"))
    @Enumerated(EnumType.STRING)
    @ForExport("Category")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 1)
    private Set<CourseType> types;
    @NotNull
    @Temporal(TemporalType.DATE)
    @ForExport("Start date")
    private Date startDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    @ForExport("End date")
    private Date endDate;
    @Column
    @ForExport("Status")
    @Enumerated(EnumType.STRING)
    private CourseStatus status = CourseStatus.DRAFT;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @ForExport("Create date")
    private Date createDate = new Date();
    @Column
    @ForExport("Publish date")
    private Date publishDate;
    @Length(max = DESCRIPTION_LENGTH)
    @Column
    @ForExport("Description")
    private String description;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "courses_tags",
      joinColumns = { @JoinColumn(name = "tagId") },
      inverseJoinColumns = { @JoinColumn(name = "id") })
    @ForExport("Tags")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 1)
    private Set<Tag> tags;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    @BatchSize(size = 1)
    @JoinTable(name = "courses_experts",
      joinColumns = { @JoinColumn(name = "expertId") },
      inverseJoinColumns = { @JoinColumn(name = "courseId") })
    @ForExport(value = "Experts")
    private Set<User> experts;

    public Course() {

    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(final Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<CourseType> getTypes() {
        return types;
    }

    public void setTypes(final Set<CourseType> types) {
        this.types = types;
    }

    public Set<User> getExperts() {
        return experts;
    }

    public void setExperts(final Set<User> experts) {
        this.experts = experts;
    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public void setStartDate(final Date startDate) {
        if (startDate != null) {
            this.startDate = new Date(startDate.getTime());
        }
    }

    public Date getEndDate() {
        return endDate == null ? null : new Date(endDate.getTime());
    }

    public void setEndDate(final Date endDate) {
        if (endDate != null) {
            this.endDate = new Date(endDate.getTime());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(final Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public CourseStatus getStatus() {
        if (status == null) {
            return CourseStatus.DRAFT;
        }
        return status;
    }

    public void setStatus(final CourseStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate == null ? null : new Date(createDate.getTime());
    }

    public void setCreateDate(final Date createDate) {
        if (createDate != null) {
            this.createDate = new Date(createDate.getTime());
        }
    }

    public Date getPublishDate() {
        return publishDate == null ? null : new Date(publishDate.getTime());
    }

    public void setPublishDate(final Date publishDate) {
        if (publishDate != null) {
            this.publishDate = new Date(publishDate.getTime());
        }
    }

    public boolean isPublished() {
        return this.status == CourseStatus.PUBLISHED;
    }



    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(o.getClass())) {
            return false;
        }

        final Course course = (Course) o;

        if (experts != null ? !experts.equals(course.experts) : course.experts != null) {
            return false;
        }
        if (getCode() != null ? !getCode().equals(course.getCode()) : course.getCode() != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(course.endDate) : course.endDate != null) {
            return false;
        }
        if (name != null ? !name.equals(course.name) : course.name != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(course.startDate) : course.startDate != null) {
            return false;
        }
        if (tags != null ? !tags.equals(course.tags) : course.tags != null) {
            return false;
        }
        if (types != null ? !types.equals(course.types) : course.types != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = getCode() != null ? getCode().hashCode() : 0;
        result = HASH_CODE_CONSTANT * result + (name != null ? name.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (types != null ? types.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (experts != null ? experts.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (startDate != null ? startDate.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (endDate != null ? endDate.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (tags != null ? tags.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (status != null ? status.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (description != null ? description.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (createDate != null ? createDate.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = HASH_CODE_CONSTANT * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return  COURSE
                + "capacity=" + capacity
                + NAME + name
                + ", types=" + types
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + ", status=" + status
                + ", createDate=" + createDate
                + ", publishDate=" + publishDate
                + ", description='" + description
                + '\''
                + ", tags=" + tags
                + ", experts=" + experts
                + '}';
    }

    @Override
    public String getLogDetail() {
        return  COURSE
                + "id=" + this.getId()
                + NAME + name
                + " ...}";
    }
}
