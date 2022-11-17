package com.study.service.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.domain.board.BoardDto;
import com.study.domain.member.MemberDto;
import com.study.mapper.board.BoardMapper;
import com.study.mapper.board.ReplyMapper;
import com.study.mapper.member.MemberMapper;
import com.study.service.board.BoardService;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public int insert(MemberDto member) {
		
		String pw = member.getPassword();
		
		member.setPassword(passwordEncoder.encode(pw));
		
		return memberMapper.insert(member);
	}

	public List<MemberDto> list() {
		// TODO Auto-generated method stub
		return memberMapper.selectAll();
	}

	public MemberDto getById(String id) {
		// TODO Auto-generated method stub
		
		return memberMapper.selectById(id);
	}

	public int modify(MemberDto member) {
		int cnt = 0;
		try {
			if(member.getPassword() != null) {
				String encodedPw = passwordEncoder.encode(member.getPassword());
				member.setPassword(encodedPw);
			}
			
			return memberMapper.update(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}
		
	public int remove(String id) {	
		// 좋아요 삭제
		boardMapper.deleteLikeByMemberId(id);
		// 댓글 삭제
		replyMapper.deleteReplyByMemberId(id);
		// 게시물 삭제
		
		List<BoardDto> boardList = boardMapper.selectByMemberId(id);
		
		for(BoardDto board : boardList) {
			boardService.remove(board.getId());
		}
		
		return memberMapper.delete(id);
	}

	public MemberDto getByEmail(String email) {
		// TODO Auto-generated method stub
		return memberMapper.selectByEmail(email);
	}

	public MemberDto getByNickName(String nickName) {
		// TODO Auto-generated method stub
		return memberMapper.selectByNickName(nickName);
	}


}
