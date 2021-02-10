
module se.iths.persistence {
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires net.bytebuddy;
    requires java.sql;
    requires java.xml.bind;
    requires com.fasterxml.classmate;
    exports se.iths.persistence;
    opens se.iths.persistence to org.hibernate.orm.core, com.google.gson;
}