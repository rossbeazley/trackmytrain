
package com.thalesgroup.rtti._2015_05_14.ldb.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.thalesgroup.rtti._2014_02_20.ldb.types.ArrayOfArrayOfCallingPoints;


/**
 * An individual service's summary details with calling points for display on a "WithDetails" departure board.
 * 
 * <p>Java class for ServiceItemWithCallingPoints complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceItemWithCallingPoints">
 *   &lt;complexContent>
 *     &lt;extension base="{http://thalesgroup.com/RTTI/2015-05-14/ldb/types}ServiceItem">
 *       &lt;sequence>
 *         &lt;element name="previousCallingPoints" type="{http://thalesgroup.com/RTTI/2014-02-20/ldb/types}ArrayOfArrayOfCallingPoints" minOccurs="0"/>
 *         &lt;element name="subsequentCallingPoints" type="{http://thalesgroup.com/RTTI/2014-02-20/ldb/types}ArrayOfArrayOfCallingPoints" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceItemWithCallingPoints", propOrder = {
    "previousCallingPoints",
    "subsequentCallingPoints"
})
public class ServiceItemWithCallingPoints
    extends ServiceItem
{

    protected ArrayOfArrayOfCallingPoints previousCallingPoints;
    protected ArrayOfArrayOfCallingPoints subsequentCallingPoints;

    /**
     * Gets the value of the previousCallingPoints property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfArrayOfCallingPoints }
     *     
     */
    public ArrayOfArrayOfCallingPoints getPreviousCallingPoints() {
        return previousCallingPoints;
    }

    /**
     * Sets the value of the previousCallingPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfArrayOfCallingPoints }
     *     
     */
    public void setPreviousCallingPoints(ArrayOfArrayOfCallingPoints value) {
        this.previousCallingPoints = value;
    }

    /**
     * Gets the value of the subsequentCallingPoints property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfArrayOfCallingPoints }
     *     
     */
    public ArrayOfArrayOfCallingPoints getSubsequentCallingPoints() {
        return subsequentCallingPoints;
    }

    /**
     * Sets the value of the subsequentCallingPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfArrayOfCallingPoints }
     *     
     */
    public void setSubsequentCallingPoints(ArrayOfArrayOfCallingPoints value) {
        this.subsequentCallingPoints = value;
    }

}
