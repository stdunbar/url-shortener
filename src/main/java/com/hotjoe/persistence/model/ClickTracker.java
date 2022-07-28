package com.hotjoe.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.OffsetDateTime;

/*
    click_tracker_id integer                  not null default nextval('click_tracker_id_seq'),
    remote_host_addr varchar(64)              not null,
    user_agent       varchar(128)             null,
    short_url_id     integer                  not null,
    create_date      timestamp with time zone not null default now(),
 */

@Entity
@Table(name="click_tracker")
public class ClickTracker {
	@Id
	@Column(name="click_tracker_id")
	@SequenceGenerator(name="click_tracker_id_seq_generator", sequenceName="click_tracker_id_seq", allocationSize = 1)
	@GeneratedValue(strategy= GenerationType.SEQUENCE, generator="click_tracker_id_seq_generator")
	private Integer clickTrackerId;

	@Column(name="remote_host_addr")
	private String remoteHostAddr;

	@Column(name="user_agent")
	private String userAgent;

	@ManyToOne
	@JoinColumn(name="short_url_id", nullable=false)
	private ShortUrl shortUrl;

	@Column(name="create_date")
	private OffsetDateTime createDate;

	public Integer getClickTrackerId() {
		return clickTrackerId;
	}

	public void setClickTrackerId(Integer clickTrackerId) {
		this.clickTrackerId = clickTrackerId;
	}

	public String getRemoteHostAddr() {
		return remoteHostAddr;
	}

	public void setRemoteHostAddr(String remoteHostAddr) {
		this.remoteHostAddr = remoteHostAddr;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public ShortUrl getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(ShortUrl shortUrl) {
		this.shortUrl = shortUrl;
	}

	public OffsetDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(OffsetDateTime createDate) {
		this.createDate = createDate;
	}
}