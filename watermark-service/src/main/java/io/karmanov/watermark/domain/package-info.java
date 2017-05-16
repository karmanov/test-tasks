@TypeDefs({
    /*
     * the following line defines org.jadira.usertype.dateandtime.joda.PersistentDateTime to be the default type being
     * used when an entity field is of type DateTime.
     */
        @TypeDef(name = "PersistentDateTime", typeClass = PersistentDateTime.class, defaultForType = DateTime.class),
})
package io.karmanov.watermark.domain;

import org.joda.time.DateTime;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;

