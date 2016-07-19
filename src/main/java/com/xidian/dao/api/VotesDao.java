package com.xidian.dao.api;

import java.util.List;
import com.xidian.forms.Votes;

public interface VotesDao {
	void addVote(Votes vote);

	void deleteVote(Votes vote);

	void updateVote(Votes vote);

	List<Votes> getVote();

	Votes getVoteByIdentity(String identity);
}
