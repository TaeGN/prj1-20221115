package com.study.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.domain.board.ReplyDto;
import com.study.service.board.ReplyService;

@RestController // @Controller + @ResponseBody
@RequestMapping("reply")
public class ReplyController {
	
	@Autowired
	private ReplyService service;
	
	@GetMapping("list/{boardId}")
	public List<ReplyDto> list(@PathVariable int boardId, Authentication authentication) {
		
		String username = "";
		
		if(authentication != null) {
			username = authentication.getName();
		}
		
		return service.listReplyByBoardId(boardId, username);
	}
	
	@GetMapping("get/{id}")
	public ReplyDto get(@PathVariable int id) {
		return service.getById(id);
	}
	
	@PutMapping("modify")
	@PreAuthorize("@replySecurity.checkWriter(authentication.name, #reply.id)")
	public Map<String, Object> modify(@RequestBody ReplyDto reply) {
		Map<String, Object> map = new HashMap<>();
		
		int cnt = service.modify(reply);
		
		if(cnt == 1) {
			map.put("message", reply.getId() + "번 댓글이 수정되었습니다.");
		} else {
			map.put("message", reply.getId() + "번 댓글이 수정되지	않았습니다.");
		}
		
		
		return map;
	}
	
	@DeleteMapping("remove/{id}")
	@PreAuthorize("@replySecurity.checkWriter(authentication.name, #id)")
	public Map<String, Object> remove(@PathVariable int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int cnt = service.removeById(id);
		if(cnt == 1) {
			map.put("message", id + "번 댓글이 삭제되었습니다.");
		} else {
			map.put("message", id + "번 댓글이 삭제되지	않았습니다.");
		}
		
		return map;
	}
	
	@PostMapping("add")
	@PreAuthorize("isAuthenticated()")
	public Map<String, Object> add(@RequestBody ReplyDto reply, Authentication authentication) {
			reply.setWriter(authentication.getName());
		
		int cnt = service.addReply(reply);
		Map<String, Object> map = new HashMap<>();
		
		if(cnt == 1) {
			map.put("message", "새 댓글이 등록되었습니다.");
		} else {
			map.put("message", "새 댓글이 등록되지 않았습니다.");
		}
		return map;
	}
}
