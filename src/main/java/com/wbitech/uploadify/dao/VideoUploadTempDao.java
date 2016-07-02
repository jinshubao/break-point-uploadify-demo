package com.wbitech.uploadify.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wbitech.uploadify.entity.VideoUploadTemp;

@Repository
public class VideoUploadTempDao {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public VideoUploadTemp find(Integer memberId, String originalFileName, long fileSize, Long lastModifiedTime) {
		List<VideoUploadTemp> list = jdbcTemplate.query(
				"select * from video_upload_temp where member_id = ? and original_file_name = ? and file_size = ? and last_modified_time = ? and status = 0",
				new Object[] { memberId, originalFileName, fileSize, lastModifiedTime }, new MyRowMapper());
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public void insert(VideoUploadTemp videoUploadTemp) {
		jdbcTemplate.update(
				"insert into video_upload_temp(original_file_name, "
						+ "file_size, last_modified_time, status, server_file_name, uploaded_size, member_id) "
						+ "value(?,?,?,?,?,?,?)",
				new Object[] { videoUploadTemp.getOriginalFileName(), videoUploadTemp.getFileSize(),
						videoUploadTemp.getLastModifiedTime(), videoUploadTemp.getStatus(),
						videoUploadTemp.getServerFileName(), videoUploadTemp.getUploadedSize(),
						videoUploadTemp.getMemberId() });
	}

	public void update(VideoUploadTemp videoUploadTemp) {
		jdbcTemplate.update(
				"update video_upload_temp set uploaded_size = ?, status = ?, server_file_name  = ? where original_file_name = ? and "
						+ "file_size = ? and last_modified_time = ? and member_id = ?",
				new Object[] { videoUploadTemp.getUploadedSize(), videoUploadTemp.getStatus(),
						videoUploadTemp.getServerFileName(), videoUploadTemp.getOriginalFileName(),
						videoUploadTemp.getFileSize(), videoUploadTemp.getLastModifiedTime(),
						videoUploadTemp.getMemberId() });
	}

	public void delete(VideoUploadTemp videoUploadTemp) {
		jdbcTemplate.update(
				"delete from video_upload_temp where original_file_name = ? and "
						+ "file_size = ? and last_modified_time = ? and member_id = ?",
				new Object[] { videoUploadTemp.getOriginalFileName(), videoUploadTemp.getFileSize(),
						videoUploadTemp.getLastModifiedTime(), videoUploadTemp.getMemberId() });
	}
}

class MyRowMapper implements RowMapper<VideoUploadTemp> {
	@Override
	public VideoUploadTemp mapRow(ResultSet rs, int rowNum) throws SQLException {
		VideoUploadTemp vut = new VideoUploadTemp();
		vut.setOriginalFileName(rs.getString("original_file_name"));
		vut.setFileSize(rs.getLong("file_size"));
		vut.setLastModifiedTime(rs.getLong("last_modified_time"));
		vut.setUploadedSize(rs.getLong("uploaded_size"));
		vut.setStatus(rs.getInt("status"));
		vut.setServerFileName(rs.getString("server_file_name"));
		vut.setMemberId(rs.getInt("member_id"));
		return vut;
	}
}
