package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JDBCTemplateMemoRepository implements MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCTemplateMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemoResponseDto saveMemo(Memo memo) {

        // INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("memo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", memo.getTitle());
        parameters.put("contents", memo.getContents());

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new MemoResponseDto(key.longValue(), memo.getTitle(), memo.getContents());
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return jdbcTemplate.query("SELECT * FROM memo", memoRowMapper());
    }

    @Override
    public Optional<Memo> findMemoById(Long id) {

        List<Memo> result = jdbcTemplate.query("SELECT * FROM memo WHERE id = ?", memoRowMapperV2(), id);

        return result.stream().findAny();
    }

    @Override
    public Memo findMemoByIdOrElseThrow(Long id) {

        List<Memo> result = jdbcTemplate.query("SELECT * FROM memo WHERE id = ?", memoRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public int updateMemo(Long id, String title, String contents) {

        // 영향을 받은 row의 수가 반환된다.
        return jdbcTemplate.update("UPDATE memo SET title = ?, contents = ? WHERE id = ?", title, contents, id);
    }

    @Override
    public int updateTitle(Long id, String title) {

        return jdbcTemplate.update("UPDATE memo SET title = ? WHERE id = ?", title, id);
    }

    @Override
    public int deleteMemo(Long id) {

        return jdbcTemplate.update("DELETE FROM memo WHERE id = ?", id);
    }

    private RowMapper<MemoResponseDto> memoRowMapper() {

        return new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
    }

    private RowMapper<Memo> memoRowMapperV2() {

        return new RowMapper<Memo>() {
            @Override
            public Memo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Memo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
    }
}
