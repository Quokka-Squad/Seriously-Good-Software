package ch6.practice;

public interface PopularityContest<T> {

    void addContestant(T contestant);

    void voteFor(T contestant);

    T getMostVoted();
}
