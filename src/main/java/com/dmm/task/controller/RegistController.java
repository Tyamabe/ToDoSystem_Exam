package com.dmm.task.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dmm.task.data.entity.Regist;
import com.dmm.task.data.repository.RegistRepository;
import com.dmm.task.form.RegistForm;
import com.dmm.task.service.AccountUserDetails;

@Controller
public class RegistController {

	@Autowired
	private RegistRepository repo;

	/**
	 * 投稿の一覧表示.
	 * 
	 * @param model モデル
	 * @return 遷移先
	 */
	@GetMapping("/create")
	public String regist(Model model) {
		// 逆順で投稿をすべて取得する
		List<Regist> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
//    Collections.reverse(list); //普通に取得してこちらの処理でもOK
		model.addAttribute("posts", list);
		RegistForm registForm = new RegistForm();
		model.addAttribute("registForm", registForm);
		return "/create";
	}

	/**
	 * 投稿を作成.
	 * 
	 * @param postForm 送信データ
	 * @param user     ユーザー情報
	 * @return 遷移先
	 */
	@PostMapping("/create")
	public String create(@Validated RegistForm registForm, BindingResult bindingResult,
			@AuthenticationPrincipal AccountUserDetails user, Model model) {
		// バリデーションの結果、エラーがあるかどうかチェック
		if (bindingResult.hasErrors()) {
			// エラーがある場合は投稿登録画面を返す
			List<Regist> list = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
			model.addAttribute("regist", list);
			model.addAttribute("registForm", registForm);
			return "/main";
		}

		Regist regist = new Regist();
		regist.setName(user.getName());
		regist.setTitle(registForm.getTitle());
		regist.setText(regist.getText());
		regist.setDate(LocalDateTime.now());

		repo.save(regist);

		return "create";
	}

	/**
	 * 投稿を削除する
	 * 
	 * @param id 投稿ID
	 * @return 遷移先
	 */
	@PostMapping("/edit")
	public String delete(@PathVariable Integer id) {
		repo.deleteById(id);
		return "main";
	}
}
