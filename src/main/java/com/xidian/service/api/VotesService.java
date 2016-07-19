package com.xidian.service.api;

import java.util.List;
import com.xidian.forms.Votes;

public interface VotesService {
	void addVote(Votes vote);

	void deleteVote(Votes vote);

	void updateVote(Votes vote);

	List<Votes> getVote();

	Votes getVoteByIdentity(String identity);
}
