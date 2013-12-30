/**
 * =============================================================================
 *
 * ORCID (R) Open Source
 * http://orcid.org
 *
 * Copyright (c) 2012-2013 ORCID, Inc.
 * Licensed under an MIT-Style License (MIT)
 * http://orcid.org/open-source-license
 *
 * This copyright and license information (including a link to the full license)
 * shall be included in its entirety in all copies or substantial portion of
 * the software.
 *
 * =============================================================================
 */
package org.orcid.jaxb.model.message;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.orcid.org/ns/orcid}amount" minOccurs="1" maxOccurs="1"/>
 *         &lt;attGroup ref="{http://www.orcid.org/ns/orcid}currency-code" minOccurs="1"/>
 *       &lt;/sequence> *    
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "content" })
@XmlRootElement(name = "amount")
public class Amount implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @XmlValue
    protected String content;
    @XmlAttribute(name = "currency-code", required = true)
    protected CurrencyCode currencyCode;

    public Amount() {

    }

    public Amount(String content) {
        this.content = content;
    }

    public Amount(String content, CurrencyCode currencyCode) {
        this.content = content;
        this.currencyCode = currencyCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + (StringUtils.isEmpty(this.content) ? 0 : this.content.hashCode());
        result = prime * result + (this.currencyCode == null ? 0 : this.currencyCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Amount other = (Amount) obj;
        if (this.content == null) {
            if (other.content != null)
                return false;
        } else if (!this.content.equals(other.content))
            return false;

        if (this.currencyCode == null) {
            if (other.currencyCode != null)
                return false;
        } else if (!this.currencyCode.equals(other.currencyCode))
            return false;

        return true;
    }
}
