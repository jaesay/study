package com.jaesay.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "boards")
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "boardId")
public class Board implements Serializable {

	private static final long serialVersionUID = 8834273571418787335L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardId;
	
	@ManyToOne
    @JoinColumn(name="member_name", 
    			referencedColumnName = "member_name", 
    			foreignKey = @ForeignKey(name = "fk_board_member"))
	private Member member;
	
	@Column(name = "title", length = 255, nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
    @Column(name="created_date", nullable = false)
    private LocalDateTime createdDate;
    
	@UpdateTimestamp
    @Column(name="updated_date", nullable = false)
    private LocalDateTime updatedDate;

	@Builder
	public Board(Long boardId, Member member, String title, String content, LocalDateTime createdDate,
			LocalDateTime updatedDate) {
		super();
		this.boardId = boardId;
		this.member = member;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}

	

}
