package com.xidian.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xidian.dao.api.BachelorTimetableDao;
import com.xidian.forms.Classtable;
import com.xidian.forms.College;
import com.xidian.forms.Semester;
import com.xidian.forms.Timetable;

@Repository("bachelorTimetableDaoImpl")
public class BachelorTimetableDaoImpl implements BachelorTimetableDao{
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public List<Semester> getSemesterList() {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Semester semester order by id desc");
		return query.list();
	}

	@Override
	public List<College> getCollegeList() {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from College college order by id asc");
		return query.list();
	}

	@Override
	public List<Classtable> getClasstableList() {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Classtable classtable order by id asc");
		return query.list();
	}

	@Override
	public List<Timetable> getTimetableBySemesterId(int semester_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Timetable timetable where timetable.semester.id = :semester_id order by timetable.id").setParameter("semester_id", semester_id);
		return query.list();
	}

	@Override
	public List<College> getCollegeListBySemesterId(int semester_id) {
		//System.out.println("½øÀ´ÁË");
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select c from College c join c.semesterList s where s.id = :semester_id order by c.id").setParameter("semester_id", semester_id);
		return query.list();
	}

	@Override
	public List<Classtable> getClasstableBySemesterIdAndCollegeId(int semester_id, int college_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select cl from Classtable cl join cl.semesterList s where s.id = :semester_id and cl.college.id = :college_id order by cl.class_name").setParameter("semester_id", semester_id).setParameter("college_id", college_id);
		return query.list();
	}

	@Override
	public Timetable getTimetableBySemesterIdAndClasstableId(int semester_id, int class_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Timetable t where t.semester.id = :semester_id and t.classtable.id = :class_id").setParameter("semester_id", semester_id).setParameter("class_id", class_id);
		List<Timetable> list = query.list();
		if(list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Classtable> getClasstableByCollegeId(int college_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select cl from Classtable cl where cl.college.id = :college_id order by class_name asc").setParameter("college_id", college_id);
		return query.list();
	}

	@Override
	public boolean addSemester(Semester semester) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Semester s where s.semester_name = :name").setParameter("name", semester.getSemester_name());
		List list = query.list();
		if(!list.isEmpty())
			return false;
		else
			session.save(semester);
		return true;
	}

	@Override
	public boolean addCollege(College college) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from College c where c.college_name = :name").setParameter("name", college.getCollege_name());
		List list = query.list();
		if(!list.isEmpty())
			return false;
		else
			session.save(college);
		return true;
	}

	@Override
	public boolean addClasstable(Classtable classtable) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select cl from Classtable cl where cl.college.id = :college_id and cl.class_name = :class_name").setParameter("college_id", classtable.getCollege().getId()).setParameter("class_name", classtable.getClass_name());
		List list = query.list();
		if(!list.isEmpty())
			return false;
		else
			session.save(classtable);
		return true;
	}

	@Override
	public boolean addTimetable(Timetable timetable) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Timetable t where t.semester.id = :semester_id and t.classtable.id = :class_id").setParameter("semester_id", timetable.getSemester().getId()).setParameter("class_id", timetable.getClasstable().getId());
		List list = query.list();
		if(!list.isEmpty())
			return false;
		session.save(timetable);
		return true;
	}
	public void timetableUpdateCollege(Timetable timetable){
		Session session=sessionFactory.getCurrentSession();
		Classtable ct = timetable.getClasstable();
		Semester s = timetable.getSemester();
		College c = ct.getCollege();
		c.getSemesterList().add(s);
		session.update(c);
	}
	public void timetableUpdateClasstable(Timetable timetable){
		Session session=sessionFactory.getCurrentSession();
		Classtable ct = timetable.getClasstable();
		System.out.println(ct.getClass_name());
		Semester s = timetable.getSemester();
		System.out.println(s.getSemester_name());
		List<Semester> semesterList = ct.getSemesterList();
		semesterList.add(s);
		session.update(ct);
	}
	@Override
	public College getCollgeById(int college_id) {
		Session session=sessionFactory.getCurrentSession();
		return (College)session.get(College.class, college_id);
	}

	@Override
	public List<Timetable> getTimetableBySemesterIdAndCollegeId(int semester_id, int college_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select timetable from Timetable timetable join timetable.classtable ct join ct.college c where timetable.semester.id = :semester_id and c.id = :college_id order by timetable.id").setParameter("semester_id", semester_id).setParameter("college_id", college_id);
		return query.list();
	}

	@Override
	public List<Timetable> getAllTimetable() {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Timetable timetable order by id asc");
		return query.list();
	}

	@Override
	public Semester getSemesterById(int semester_id) {
		Session session=sessionFactory.getCurrentSession();
		return (Semester)session.get(Semester.class, semester_id);
	}

	@Override
	public Classtable getClasstableById(int class_id) {
		Session session=sessionFactory.getCurrentSession();
		return (Classtable)session.get(Classtable.class, class_id);
	}

	@Override
	public boolean delSemester(int semester_id) {
		Session session=sessionFactory.getCurrentSession();
		Semester semester = (Semester)session.get(Semester.class, semester_id);
		if(semester != null) {
			for(Timetable t : semester.getTimetableList()){
				t.getClasstable().getTimetableList().remove(t);
			}
			session.delete(semester);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean delCollege(int college_id) {
		College college = getCollgeById(college_id);
		if(college != null) {
			Session session=sessionFactory.getCurrentSession();
			for(Classtable classtable : college.getClassList()){
				for(Timetable t : classtable.getTimetableList()){
					t.getSemester().getTimetableList().remove(t);
				}
			}
			session.delete(college);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean delClasstable(int class_id) {
		Session session=sessionFactory.getCurrentSession();
		Classtable classtable = (Classtable)session.get(Classtable.class, class_id);
		if(classtable != null) {
			
			for(Timetable t : classtable.getTimetableList()){
				//classtable.getTimetableList().remove(t);
				t.getSemester().getTimetableList().remove(t);
				//session.delete(t);
				//delTimetable(t);
			}
			classtable.getCollege().getClassList().remove(classtable);
			//classtable.setCollege(null);
			session.delete(classtable);
			return true;
		}
		else 
			return false;
	}

	@Override
	public boolean delTimetable(int timetable_id) {
		Session session=sessionFactory.getCurrentSession();
		Timetable timetable= (Timetable)session.get(Timetable.class, timetable_id);
		if(timetable != null){
			timetable.getSemester().getTimetableList().remove(timetable);
			timetable.getClasstable().getTimetableList().remove(timetable);
			session.delete(timetable);
			return true;
		}
		else
			return false;
	}

	@Override
	public void updateSemester(Semester semester) {
		Session session=sessionFactory.getCurrentSession();
		session.update(semester);
	}

	@Override
	public void updateCollege(College college) {
		Session session=sessionFactory.getCurrentSession();
		session.update(college);
	}

	@Override
	public void updateClasstable(Classtable classtable) {
		Session session=sessionFactory.getCurrentSession();
		session.update(classtable);
	}

	@Override
	public void updateTimetable(Timetable timetable) {
		Session session=sessionFactory.getCurrentSession();
		session.update(timetable);
	}

	@Override
	public Timetable getTimetableById(int timetable_id) {
		Session session=sessionFactory.getCurrentSession();
		return (Timetable)session.get(Timetable.class, timetable_id);
	}

	@Override
	public List<Classtable> getClasstableBySemesterId(int semester_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select ct from Classtable ct join ct.semesterList s where s.id = :semester_id").setParameter("semester_id", semester_id);
		return query.list();
	}

	@Override
	public void delTimetable(Timetable timetable) {
		Session session=sessionFactory.getCurrentSession();
		session.delete(timetable);
	}

	@Override
	public long getAllTimetableCount() {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Timetable timetable");
		return (Long)query.uniqueResult();
	}

	@Override
	public long getTimetableCountBySemesterId(int semester_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Timetable timetable where timetable.semester.id = :semester_id").setParameter("semester_id", semester_id);
		return (Long)query.uniqueResult();
	}

	@Override
	public long getTimetableCountBySemesterIdAndCollegeId(int semester_id, int college_id) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Timetable timetable join timetable.classtable ct join ct.college c where timetable.semester.id = :semester_id and c.id = :college_id").setParameter("semester_id", semester_id).setParameter("college_id", college_id);
		return (Long)query.uniqueResult();
	}

	@Override
	public List<Timetable> getTimetableForPage(int page, int pageSize) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Timetable timetable order by id asc");
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public List<Timetable> getTimetableBySemesterIdForPage(int semester_id, int page, int pageSize) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Timetable timetable where timetable.semester.id = :semester_id order by timetable.id").setParameter("semester_id", semester_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	@Override
	public List<Timetable> getTimetableBySemesterIdAndCollegeIdForPage(int semester_id, int college_id, int page,
			int pageSize) {
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery("select timetable from Timetable timetable join timetable.classtable ct join ct.college c where timetable.semester.id = :semester_id and c.id = :college_id order by timetable.id").setParameter("semester_id", semester_id).setParameter("college_id", college_id);
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		return query.list();
	}

	

}
