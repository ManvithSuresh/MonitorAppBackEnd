//package com.vis.monitor.ws.modal;
//
//import java.util.List;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import org.springframework.http.HttpMethod;
//
//@Entity
//@Table(name = "web_services" )
//@lombok.Data
//public class WebService {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "wsIdSeq")
//	private Long id;
//	
//	private String name;
//	
//	private String url;
//	
//	@Enumerated(EnumType.STRING)
//	private HttpMethod httpMethod;
//	
//	private String requestBody;
//	
//	private String contentType;
//	
//	private String accept;
//	
//	private Integer timeout = 10;
//	
//	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.ALL} ,  fetch = FetchType.EAGER)
//    @JoinColumn(name = "parameters", referencedColumnName = "id")
//	private List<KeyValue> parameters;
//	
//	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.ALL} ,  fetch = FetchType.EAGER)
//    @JoinColumn(name = "headers", referencedColumnName = "id")
//	private List<KeyValue> headers;
//	
//
//	
//	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.ALL},  fetch = FetchType.EAGER)
//    @JoinColumn(name = "requestData", referencedColumnName = "id")
//	private List<Data> requestData;
//	
//	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE, CascadeType.ALL},  fetch = FetchType.EAGER)
//    @JoinColumn(name = "responseData", referencedColumnName = "id")
//	private List<Data> responseData;
//	
//	private Boolean isPrivate = false;
//	
//	private Boolean disableCertificateAuthentication = false;
//
//	
//	
//
//}


package com.vis.monitor.ws.modal;

import javax.persistence.*;
import java.util.List;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.http.HttpMethod;

@Entity
@Table(name = "web_services")
@lombok.Data
public class WebService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "wsIdSeq")
    private Long id;

    private String name;

    private String url;

    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

    private String requestBody;

    private String contentType;

    private String accept;

    private Integer timeout = 10;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parameters", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    private List<KeyValue> parameters;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "headers", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    private List<KeyValue> headers;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "requestData", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Data> requestData;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "responseData", referencedColumnName = "id")
    @Fetch(FetchMode.SUBSELECT)
    private List<Data> responseData;


    private Boolean isPrivate = false;
    
    private Boolean isActive = false;

    private Boolean disableCertificateAuthentication = false;
}

