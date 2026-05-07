package com.bookrental.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "examplers")
@Getter @Setter @NoArgsConstructor
public class Exampler {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Note: .IDENTITY is using Auto-increment, Hibernate doesn't know the entity id => multiple interrogations and lost time because the insert thread is "paused";
    @SequenceGenerator(name = "exampler_seq_gen", sequenceName = "exampler_sequence", allocationSize = 50) // Note: allocationSize is about ids necessity specifically for this Table; this will represent the number of ids recieved from the DB;
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDateTime updateTime;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "library_id")
    private Library library;

    @OneToMany(mappedBy = "exampler")
    private List<Rental> rentals;

}
