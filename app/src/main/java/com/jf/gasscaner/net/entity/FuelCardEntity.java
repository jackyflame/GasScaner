package com.jf.gasscaner.net.entity;

import com.haozi.baselibrary.net.entity.BaseNetEntity;

import java.util.Date;
import java.lang.String;

/**   
 * @Title: Entity
 * @Description: 加油卡
 * @author onlineGenerator
 * @date 2017-08-14 12:10:23
 * @version V1.0   
 *
 */
@SuppressWarnings("serial")
public class FuelCardEntity extends BaseNetEntity {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**姓名*/
	private String xm;
	/**身份证号*/
	private String sfzh;
	/**地址单位名称*/
	private String dz;
	/**车型品牌*/
	private String cx;
	/**加油卡号*/
	private String jykh;
	/**发动机车牌车架号*/
	private String hm;
	/**定点加油库*/
	private String ddjyk;
	/**单位名称*/
	private String dwmc;
	/**个体工商户担保人*/
	private String dbrxm;
	/**担保人身份证号*/
	private String dbrsfzh;
	/**指定加油人*/
	private String zdjyr;
	/**指定加油人身份证号*/
	private String zdjrysfzh;
	/**购油种类*/
	private String gyzl;
	/**加油卡类型*/
	private String jyklx;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getCx() {
		return cx;
	}

	public void setCx(String cx) {
		this.cx = cx;
	}

	public String getJykh() {
		return jykh;
	}

	public void setJykh(String jykh) {
		this.jykh = jykh;
	}

	public String getHm() {
		return hm;
	}

	public void setHm(String hm) {
		this.hm = hm;
	}

	public String getDdjyk() {
		return ddjyk;
	}

	public void setDdjyk(String ddjyk) {
		this.ddjyk = ddjyk;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDbrxm() {
		return dbrxm;
	}

	public void setDbrxm(String dbrxm) {
		this.dbrxm = dbrxm;
	}

	public String getDbrsfzh() {
		return dbrsfzh;
	}

	public void setDbrsfzh(String dbrsfzh) {
		this.dbrsfzh = dbrsfzh;
	}

	public String getZdjyr() {
		return zdjyr;
	}

	public void setZdjyr(String zdjyr) {
		this.zdjyr = zdjyr;
	}

	public String getZdjrysfzh() {
		return zdjrysfzh;
	}

	public void setZdjrysfzh(String zdjrysfzh) {
		this.zdjrysfzh = zdjrysfzh;
	}

	public String getGyzl() {
		return gyzl;
	}

	public void setGyzl(String gyzl) {
		this.gyzl = gyzl;
	}

	public String getJyklx() {
		return jyklx;
	}

	public void setJyklx(String jyklx) {
		this.jyklx = jyklx;
	}
}
