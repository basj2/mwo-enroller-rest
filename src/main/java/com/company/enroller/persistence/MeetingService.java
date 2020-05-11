package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	Session session;

	public MeetingService() {
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Meeting> getAll() {
		return session.createCriteria(Meeting.class).list();
		// String hql = "FROM Meeting ORDER BY title";
		// Query query = session.createQuery(hql);
		// return query.list();
	}

	public Collection<Meeting> getAllOrderedByTitle() {
		return session.createCriteria(Meeting.class).addOrder(Order.asc("title")).list();
	}

	public Collection<Meeting> searchByTitle(String search) {
		Criteria cr = session.createCriteria(Meeting.class);
		Criterion c = Restrictions.like("title", "%" + search + "%");
		cr.add(c);
		return cr.list();
	}

	public Collection<Meeting> searchByDescription(String search) {
		Criteria cr = session.createCriteria(Meeting.class);
		Criterion c = Restrictions.like("description", "%" + search + "%");
		cr.add(c);
		return cr.list();
		// return session.createCriteria(Meeting.class).list();
		//String hql = "FROM Meeting WHERE description LIKE '%" + search + "%'";
		//Query query = session.createQuery(hql);
		//return query.list();
	}

	public Collection<Meeting> searchByParticipant(String login) {
		String hql = "FROM Meeting m JOIN FETCH m.participants p "
				+ "WHERE p.login = '" + login + "'";
		Query query = session.createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id) {
		return (Meeting) session.get(Meeting.class, id);
	}

	public Meeting add(Meeting meeting) {

		Transaction transaction = this.session.beginTransaction();
		session.save(meeting);
		transaction.commit();
		return meeting;
	}

	public Collection<Participant> addParticipant(Meeting meeting, Participant participant) {

		Transaction transaction = this.session.beginTransaction();
		meeting.addParticipant(participant);
		session.save(meeting);
		transaction.commit();

		return meeting.getParticipants();
	}

	public Collection<Participant> getParticipants(Meeting meeting) {
		return meeting.getParticipants();
	}

	public void delete(Meeting meeting) {
		Transaction transaction = this.session.beginTransaction();
		session.delete(meeting);
		transaction.commit();
	}

	public void changeTitle(Meeting meeting, String title) {
		meeting.setTitle(title);
		Transaction transaction = this.session.beginTransaction();
		session.save(meeting);
		transaction.commit();
	}

	public void changeDescription(Meeting meeting, String description) {
		meeting.setDescription(description);
		Transaction transaction = this.session.beginTransaction();
		session.save(meeting);
		transaction.commit();
	}

	public void changeDate(Meeting meeting, String date) {
		meeting.setDate(date);
		Transaction transaction = this.session.beginTransaction();
		session.save(meeting);
		transaction.commit();
	}

	public Collection<Participant> deleteParticipant(Meeting meeting, Participant participant) {

		Transaction transaction = this.session.beginTransaction();
		meeting.removeParticipant(participant);
		session.save(meeting);
		transaction.commit();
		return meeting.getParticipants();
	}

}
