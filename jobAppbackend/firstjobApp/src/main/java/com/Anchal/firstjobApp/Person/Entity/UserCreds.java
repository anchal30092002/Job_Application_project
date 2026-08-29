package com.Anchal.firstjobApp.Person.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
//@Embeddable  //we are using this annotation because spring boot doesn't know how to make primary key of a class i.e user defined class . it can only make primary key of predefined data type by itself . taht's why we need to explicilty tell spring boot that hey this is a userdefined class and you have to make primary key using this class.
//it is necessary to add equals and hashcode method in this class because
public class compositeKey implements serializable {
    //Question: Why should an @Embeddable class implement Serializable?
    //"@Embeddable classes are commonly used as composite primary keys with @EmbeddedId. JPA requires composite key classes to implement Serializable because Hibernate may serialize them for caching, session management, or transferring objects across JVMs. Implementing Serializable allows the object to be converted into a byte stream and reconstructed later. It is also a requirement of the JPA specification for composite key classes."
    //in short serializable isliye use krte h kyuki springboot primary key ko store byte me convert krte krta hai to jb predefined use kr rhe the tb to springboot ko pata tha ki kaise convert krte hai but userdefined me springboot ko pata nhi hai to bs batane ke liye
    //other example is ki like java json ko java object me transform krte h api calling ke time taki jvm to object hi pehchanta hai  iss process ko deserialzable kehte h
    // and serializable iska vice versa.
    private String emailId;
    private String password;

    public compositeKey(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public compositeKey() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        compositeKey that = (compositeKey) o;
        return Objects.equals(emailId, that.emailId) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailId, password);
    }
    //Question: Why are equals() and hashCode() mandatory for an @Embeddable class?
    //"@Embeddable classes are often used as composite primary keys. Hibernate uses these keys in the persistence context, caches, and collections such as HashMap and HashSet. By default, equals() compares object references rather than field values, so two objects representing the same database row would be considered different. Overriding equals() ensures comparison is based on the key fields, while hashCode() ensures equal objects produce the same hash value. Together, they allow Hibernate to correctly identify, cache, and retrieve entities.
}
**/

// upar wale code me composite key primary key ki tarah use kr rhe the but password ko primary key nhi bna sakte h isliye ab composite key ek table hai jisme email pass store kar rhe h and user authenticate krenge.
@Entity
public class UserCreds
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String emailId;
    private String password;
    private boolean isActiveSession;


    public UserCreds() {
    }

    public UserCreds(Long id, String emailId, String password, boolean isActiveSession) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
        this.isActiveSession = isActiveSession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActiveSession() {
        return isActiveSession;
    }

    public void setActiveSession(boolean activeSession) {
        isActiveSession = activeSession;
    }
}

/** @GeneratedValue(strategy = GenerationType.SEQUENCE)
 *  @GeneratedValue(strategy = GenerationType.IDENTITY)
 *
 *  difference between these 2 is that ki identity ka time complexity jyda h becuase evrey time before insertion hibernate ask db to generate a value , db generates and then send it to hibernate . this whole process takes time
 *  but with sequenece hibernate can ask for more than one value at a time and then insert values in the table and it will takes less time .
 *  sequence me hibernate ek sequence table use krta h and usme sare sequence store krke rakhta h phle se and jb bhi hibernate db ko bolta h ki hmko ek id chahiye db uss sequence databse se value utha kr de deta h. database me phle se values store hoti hai isi wajah se ek baar me ek se jyda id hibernate ko mil jata h.
 *
 *  now here one thing to note is ki agar sirf
 *   @GeneratedValue(strategy = GenerationType.SEQUENCE) likhte h tb ye bhi ek baar me ek hi value deta h agar hm chahte h ki ek se jyda value de tb define krna hota h
 *
 *   @Id
 *     @GeneratedValue(
 *         strategy = GenerationType.SEQUENCE,
 *         generator = "person_seq"
 *     )
 *     @SequenceGenerator(
 *         name = "person_seq",
 *         sequenceName = "person_seq",  //ye sequence database ka naam hai like tcs me har sequence ka naam dete h
 *          initialValue = 1000,   //to define initial value .
 *         allocationSize = 1    // ye define kr rhe h ki ek baar me kitna value chahiye
 *     )
 *     private Long id;
 * }
 *
 *
 * sequence use krne ka yahi faida hai ki sequqnce databse ka naam de sakte h , ek baar me kitna valye return aaye ye define kr sakte h aur to aur initial value kaha se start ho wo bhi define kr sakte h
**/