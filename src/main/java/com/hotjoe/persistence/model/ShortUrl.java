package com.hotjoe.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.List;


@Entity
@Table(name="short_url")
public class ShortUrl  {
	@Id
	@Column(name="short_url_id")
	@SequenceGenerator(name="short_url_id_seq_generator", sequenceName="short_url_id_seq", allocationSize = 1)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="short_url_id_seq_generator")
	private Integer shortUrlId;


	@Column(name="short_url_encrypted_id", insertable = false, updatable = false )
	private Integer shortUrlEncryptedId;

	@Column(name="original_url")
	private String originalUrl;

	@Column(name="click_limit")
	private Integer clickLimit;

	@Column(name="click_count")
	private Integer clickCount;

	@Column(name="issue_date")
	private OffsetDateTime issueDate;

	@Column(name="expiration_date")
	private OffsetDateTime expirationDate;

	@OneToMany(mappedBy="shortUrl")
	private List<ClickTracker> clickTrackers;

	public OffsetDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(OffsetDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Integer getShortUrlId() {
		return shortUrlId;
	}

	public void setShortUrlId(Integer shortUrlId) {
		this.shortUrlId = shortUrlId;
	}

	public Integer getShortUrlEncryptedId() {
		return shortUrlEncryptedId;
	}

	public void setShortUrlEncryptedId(Integer shortUrlEncryptedId) {
		this.shortUrlEncryptedId = shortUrlEncryptedId;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public Integer getClickLimit() {
		return clickLimit;
	}

	public void setClickLimit(Integer clickLimit) {
		this.clickLimit = clickLimit;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public OffsetDateTime getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(OffsetDateTime issueDate) {
		this.issueDate = issueDate;
	}

	public List<ClickTracker> getClickTrackers() {
		return this.clickTrackers;
	}

	public void setClickTrackers(List<ClickTracker> clickTrackers) {
		this.clickTrackers = clickTrackers;
	}

	public ClickTracker addClickTracker(ClickTracker clickTracker) {
		getClickTrackers().add(clickTracker);
		clickTracker.setShortUrl(this);

		return clickTracker;
	}

	public ClickTracker removeClickTracker(ClickTracker clickTracker) {
		getClickTrackers().remove(clickTracker);
		clickTracker.setShortUrl(null);

		return clickTracker;
	}
}