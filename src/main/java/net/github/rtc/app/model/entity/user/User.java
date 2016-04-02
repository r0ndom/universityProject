package net.github.rtc.app.model.entity.user;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.Auditable;
import net.github.rtc.util.annotation.ForExport;
import net.github.rtc.util.annotation.validation.Number;
import net.github.rtc.util.annotation.validation.Validatable;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.jasypt.hibernate4.type.EncryptedStringType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@TypeDef(
  name = "encryptedString",
  typeClass = EncryptedStringType.class,
  parameters = { @Parameter(name = "encryptorRegisteredName", value = "strongHibernateStringEncryptor") })

@Entity
@Validatable
public class User extends AbstractPersistenceObject implements UserDetails, Auditable, SocialUserDetails {

    public static final int PRIMARY_LENGTH = 50; //FUCKING FUCK!!!FUUU11!!11!
    public static final int SECONDARY_LENGTH = 30;
    public static final String SPACE = " ";
    public static final int LARGE_LENGTH = 255;
    public static final int SIGN_IN_PROVIDER_LENGTH = 20;

    @NotEmpty
    @Length(max = PRIMARY_LENGTH)
    @Column
    @ForExport("Surname")
    private String surname;

    @NotEmpty
    @Length(max = PRIMARY_LENGTH)
    @Column
    @ForExport("Name")
    private String name;

    @Length(max = PRIMARY_LENGTH)
    @Column
    @ForExport("Middle name")
    private String middleName;

    @NotNull
    @Column
    @ForExport("Birthday")
    private Date birthDate;

    @NotEmpty
    @Number
    @Column
    @ForExport("Phone")
    private String phone;

    @NotEmpty
    @Email
    @Column
    @ForExport("Email")
    private String email;

    @Length(max = SECONDARY_LENGTH)
    @Column
    @ForExport("City")
    private String city;

    @Length(max = SECONDARY_LENGTH)
    @Column
    @ForExport("University")
    private String university;

    @Length(max = SECONDARY_LENGTH)
    @Column
    @ForExport("Faculty")
    private String faculty;

    @Length(max = SECONDARY_LENGTH)
    @Column
    @ForExport("Speciality")
    private String speciality;

    @NotNull
    @Column
    @ForExport("English")
    @Enumerated(EnumType.STRING)
    private EnglishLevel english;

    @NotEmpty
    @Column
    @Length(max = LARGE_LENGTH)
    @ForExport("Note")
    private String note;

    @NotEmpty
    @Column
    @Type(type = "encryptedString")
    private String password;

    @Column
    @ForExport("Gender")
    private String gender = "Male";

    @Column
    private String photo;

    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserProgLanguages",
      joinColumns = @JoinColumn(name = "user_id"))
    @ForExport("Programming Languages")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 1)
    private Set<String> programmingLanguages;

    @NotNull
    @Column
    @ForExport("Register Date")
    private Date registerDate;

    @Column
    private Date removalDate;

    @NotNull
    @Column
    @ForExport("Status")
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.INACTIVE;

    /* Spring Security fields*/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Role",
      joinColumns = { @JoinColumn(name = "USER_ID") },
      inverseJoinColumns = { @JoinColumn(name = "id") })
    @ForExport("Role")
    private List<Role> authorities;

    @Column
    private boolean accountNonExpired = true;
    @Column
    private boolean accountNonLocked = true;
    @Column
    private boolean credentialsNonExpired = true;
    @Column
    private boolean enabled = true;
    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = SIGN_IN_PROVIDER_LENGTH)
    private SocialMediaService socialSignInProvider;

    public User() {
    }

    public User(
      final String name, final String surname, final String middleName, final String email, final String password) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.email = email;
        this.password = password;
    }

    public EnglishLevel getEnglish() {
        return this.english;
    }

    public void setEnglish(final EnglishLevel english) {
        this.english = english;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(final String photo) {
        this.photo = photo;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Set<String> getProgrammingLanguages() {
        return this.programmingLanguages;
    }

    public void setProgrammingLanguages(final Set<String> progrLanguage) {
        this.programmingLanguages = progrLanguage;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(final List<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(final boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(final boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(final boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(final String university) {
        this.university = university;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(final String faculty) {
        this.faculty = faculty;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(final String speciality) {
        this.speciality = speciality;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate == null ? null : new Date(birthDate.getTime());
    }

    public void setBirthDate(final Date birthDate) {
        if (birthDate != null) {
            this.birthDate = new Date(birthDate.getTime());
        }
    }

    public Date getRegisterDate() {
        return registerDate == null ? null : new Date(registerDate.getTime());
    }

    public void setRegisterDate(final Date registerDate) {
        if (registerDate != null) {
            this.registerDate = new Date(registerDate.getTime());
        }
    }

    public Date getRemovalDate() {
        return removalDate == null ? null : new Date(removalDate.getTime());
    }

    public void setRemovalDate(final Date removalDate) {
        if (removalDate != null) {
            this.removalDate = new Date(removalDate.getTime());
        }
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(final UserStatus status) {
        this.status = status;
    }

    public boolean hasRole(final String role) {
        boolean hasRole = false;
        for (final GrantedAuthority authority : this.getAuthorities()) {
            if (authority.getAuthority().equals(role)) {
                hasRole = true;
                break;
            }
        }
        return hasRole;
    }

    public boolean isForRemoval() {
        return this.status == UserStatus.FOR_REMOVAL;
    }

    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    public String getSurnameName() {
        return this.surname + SPACE + this.name;
    }

    public SocialMediaService getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public void setSocialSignInProvider(SocialMediaService socialSignInProvider) {
        this.socialSignInProvider = socialSignInProvider;
    }

    //todo:refactor
    @Override
    public String toString() {
        return new StringBuilder(this.name).append(SPACE).append(this.surname).toString();
    }

    //is this shorter?
    public String shortString() {
        return new StringBuilder(this.name).append(SPACE).append(this.surname).append(SPACE).append(
                this.email).toString();
    }

    @Override
    public String getLogDetail() {
        return "User {"
                + "id=" + this.getId()
                + ", surname=" + surname
                + ", name=" + name
                + " ...}";
    }

    @Override
    public String getUserId() {
        return this.getUsername();
    }
}

