package com.myblog.entitys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name="Blog")
public class Blogs {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int cId;
	private String title;
	@Column(length=8000)
	private String blogPost;
	private String image;
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="dd-MMM-YYYY")
	private Date timestamp;
	
	@PrePersist
	private void onCreate()
	{
		timestamp=new Date();
	}
	
	@ManyToOne
    private User user;
	
	@ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();
	public Blogs() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public Blogs(int cId, String title, String blogPost, String image, User user, List<Tag> tags) {
		super();
		this.cId = cId;
		this.title = title;
		this.blogPost = blogPost;
		this.image = image;
		this.user = user;
		this.tags = tags;
	}
	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public Date getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getBlogPost() {
		return blogPost;
	}
	public void setBlogPost(String blogPost) {
		this.blogPost = blogPost;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
