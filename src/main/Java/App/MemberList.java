package App;

import java.util.ArrayList;

public class MemberList {

	private ArrayList<Member> mList;
	private int count;

	public MemberList(ArrayList<Member> mList) {
		this.mList = mList;
		count = mList.size();
	}

	public int getCount() {
		return this.count;
	}

	public void addMember(Member newMember) {
		if(existsMember(newMember))
			System.out.println(newMember+ " is already a member with us!");
		else {
			mList.add(newMember);
			count++;
//			System.out.println(newMember.getName()+ " was added!");
		}
	}
	
	public void removeMember(Member removedMember) {
		if(existsMember(removedMember))
			mList.remove(removedMember);
		else 
			System.out.println(removedMember+ " is not a member.");
	}

	public boolean existsMember(Member newMember) {
		for(Member m:mList) {
			if(newMember.getName().equalsIgnoreCase(m.getName()) && (newMember.getAddress().equalsIgnoreCase(m.getAddress())))	
				return true;
		}
		return false;
	}

	public Member findMemberByNameAndAddress(String name, String address) {
		for(Member m : mList) {
			if(m.getName().equalsIgnoreCase(name) && (m.getAddress().equalsIgnoreCase(address)))
				return m;
		}
		return null;
	}

	public Member findMemberByPhoneNumber(String number) {
		if(number.length() == 10) 
			number = number.substring(0, 3) + "-" + number.substring(3,6)+"-"+number.substring(6,10);
		for(Member m : mList) {
			if(m.getPhoneNumber().equals(number))
				return m;
		}
		return null;
	}

	public void seeMembers() {
		if(getCount() == 0)
			System.out.println("We have no members.");
		else {

			for(Member m:mList) {
				System.out.println(m);
			}
		}
	}
	
	public void writeToXML() {
		UserXMLWriter uxmlw = new UserXMLWriter();
		uxmlw.writeForMembers(mList);
	}
}
