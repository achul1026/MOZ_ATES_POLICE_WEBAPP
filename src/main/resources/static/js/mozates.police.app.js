const MozAtesPoliceApp = (function(){
    const app = this;
    function addListenerMulti(el, s, fn) {
        s.split(' ').forEach(e => el.addEventListener(e, fn, false));
    }
    app.loading = function (text = "loading") {
        const loading = this;
        let loading_cover = null;
       	    const loading_html = `<div id="loading-cover">
	    						 <div id="loadingContainer">
	    						 	 <div class="scaling-dots"><div></div><div></div><div></div></div>
	               					 <p id="loadingText">${text}</p>
	    						 </div>
	       					  </div>`;
        loading.start = function () {
            loading_cover = document.createElement('div');
            loading_cover.innerHTML = loading_html;
            document.body.appendChild(loading_cover);
            return loading;
        }
        loading.end = function () {
            loading_cover.remove();
            loading_cover = null;
        }
        return loading;
    }

    app.setValidateMsg = function(element){
        const nodeList =  document.querySelectorAll("input, select");
        const targets = Array.from(nodeList);
        targets.filter((obj) => obj.getAttribute("title")).forEach((element) => {
            addListenerMulti(element, "focus change invalid", () => {
                //element.reportValidity();
                element.setCustomValidity("");
                if(!element.validity?.valid)
                    element.setCustomValidity(element.getAttribute("title"));
            });
        })
    }

    app.addAccidentParty = function(
        {partyTitle,vehicleNo,dvYn,driverLicenseId,name,phone,damage,dvY,dvN,injury1,injury2,injury3,injury4,injury5, addPassenger}){
        const currentIdx = document.querySelectorAll('.accident-party-wrap').length + 1;
        let html = `
                <input type="hidden" name="party_passenger_num_${currentIdx}" value="${currentIdx}">
                <div class="formRegistTitle">
                    <h2><span class="party_Title">${partyTitle}</span> <span class="party_cnt">${currentIdx}</span></h2>
                </div>
                <div class="formRegistList">
                    <label class="" for="vhRegNo_${currentIdx}" >${vehicleNo}</label>
                    <input type="text" id="vhRegNo_${currentIdx}" name="vhRegNo_${currentIdx}" placeholder="${vehicleNo}" class="">
                </div>
                
                <div class="accident-party-passenger-table">
                	<div class="formRegistList">
	                	<label>${dvYn}</label>
	                	 <select name="dvYn_${currentIdx}_1">
	                        <option value="N">${dvN}</option>
	                        <option value="Y">${dvY}</option>
	                    </select>
	                </div>
	                <div class="formRegistList">
	                	<label>${driverLicenseId}</label>
	                    <input type="hidden" name="passenger_del_yn_${currentIdx}_1" class="passenger_del_yn" value="N">
	                    <input type="text" name="dvrLcenId_${currentIdx}_1" placeholder="${driverLicenseId}">
	                </div>
	                <div class="formRegistList">
	                	<label>${name}</label>
	                    <input type="text" name="pnrNm_${currentIdx}_1" placeholder="${name}">
	                </div>
	                <div class="formRegistList">
	                	<label>${phone}</label>
	                    <input type="text" name="pnrPno_${currentIdx}_1" placeholder="${phone}">
	                </div>
	                <div class="formRegistList">
	                	<label>${damage}</label>
	                     <select name="pnrDmgCd_${currentIdx}_1">
	                        <option value="PDC001" >${injury1}</option>
	                        <option value="PDC002">${injury2}</option>
	                        <option value="PDC003">${injury3}</option>
	                        <option value="PDC004">${injury4}</option>
	                        <option value="PDC005">${injury5}</option>
	                     </select>
	                </div>
                </div>
	
                <button type="button" class="addBtn" onclick="MozAtesPoliceApp.addAccidentPassenger(${currentIdx})"><img src="/images/table-plus.png"> ${addPassenger}</button>`;
        const wrap = document.createElement('div');
        wrap.dataset.partyNo = currentIdx;
        wrap.className= "crackdown-wrap accident-party-wrap";
        wrap.innerHTML = html;

        document.querySelector("#accident-party-container").appendChild(wrap);
    }
    
    app.addAccidentPartyV2 = function({
			partyTitle,
        	driverLicenseId,
        	accidentTarget,
        	accidentTargetCd1,
        	accidentTargetCd2,
        	vehicleNo,
        	vehicleType,
        	driveYn,
        	driveY,
        	driveN,
        	accidentDamage,
        	accidentDamageCd1,
        	accidentDamageCd2,
        	accidentDamageCd3,
        	accidentDamageCd4,
        	targetPhone,
        	targetBirth,
        	targetName,
        	deadYn,
        	deadY,
        	deadN,
        	passenger,
        	addPassenger
    	}) {
		const currentIdx = document.querySelectorAll('.accident-party-wrap').length;
		
	    let html = `
        <button type="button" class="partyClose" data-index="${currentIdx}" onclick="MozAtesPoliceApp.deleteAccidentParty(this)"><img src="/images/close.png"></button>
        <div class="formRegistTitle">
            <h2><span class="party_Title">${partyTitle}</span> <span class="party_cnt">${currentIdx+1}</span></h2>
        </div>
        <div class="formRegistList">
            <label for="accidentTarget_${currentIdx}">${accidentTarget}</label>
            <select id="accidentTarget_${currentIdx}" class="partyAccidentTarget" data-index="${currentIdx}" onchange="MozAtesPoliceApp.viewVehicleWrap(this)">
                <option value="ATT000" >${accidentTargetCd1}</option>
                <option value="ATT001">${accidentTargetCd2}</option>
            </select>
        </div>
        <div id="vehicle-wrap_${currentIdx}" class="vehicleWrap" data-index="${currentIdx}">
            <div class="formRegistList">
                <label for="driverLicenseId_${currentIdx}" >${driverLicenseId}</label>
                <input type="text" id="driverLicenseId_${currentIdx}" class="partyDriverLiecenseId" placeholder="${driverLicenseId}">
            </div>
            <div class="formRegistList">
                <label for="vhRegNo_${currentIdx}">${vehicleNo}</label>
                <input type="text" id="vhRegNo_${currentIdx}" class="partyVhRegNo" placeholder="${vehicleNo}">
            </div>
            <div class="formRegistList">
                <label for="vhTy_${currentIdx}">${vehicleType}</label>
                <input type="text" id="vhTy_${currentIdx}" class="partyVhty" placeholder="${vehicleType}">
            </div>
            <div class="formRegistList">
                <label for="dvYn_${currentIdx}">${driveYn}</label>
                <select id="dvYn_${currentIdx}" class="partyDvYn">
                    <option value="Y" >${driveY}</option>
                    <option value="N">${driveN}</option>
                </select>
            </div>
        </div>
        <div class="formRegistList">
            <label for="acdntDmgCd_${currentIdx}">${accidentDamage}</label>
            <select id="acdntDmgCd_${currentIdx}" class="partyAcdntDmgCd">
                <option value="ADC000" >${accidentDamageCd1}</option>
                <option value="ADC001">${accidentDamageCd2}</option>
                <option value="ADC002">${accidentDamageCd3}</option>
                <option value="ADC003">${accidentDamageCd4}</option>
            </select>
        </div>
        <div class="formRegistList">
            <label for="acdntTrgtPno_${currentIdx}">${targetPhone}</label>
            <input type="text" id="acdntTrgtPno_${currentIdx}" class="partyAcdntTrgtPno" placeholder="${targetPhone}">
        </div>
        <div class="formRegistList">
            <label for="acdntTrgtBrth_${currentIdx}">${targetBirth}</label>
            <input type="text" id="acdntTrgtBrth_${currentIdx}" inputmode="numeric" class="partyAcdntTrgtBrth" placeholder="dd.mm.aaaa" maxlength="10" onkeyup="keyupDateCheck(event, 'ddMMyyyy', '.')">
        </div>
        <div class="formRegistList">
            <label for="acdntTrgtNm_${currentIdx}">${targetName}</label>
            <input type="text" id="acdntTrgtNm_${currentIdx}" class="partyAcdntTrgtNm" placeholder="${targetName}">
        </div>
        <div class="formRegistList">
            <label for="deadYn_${currentIdx}">${deadYn}</label>
            <select id="deadYn_${currentIdx}" class="partyDeadYn">
                <option value="Y" >${deadY}</option>
                <option value="N">${deadN}</option>
            </select>
        </div>
        <div id="passenger-wrap_${currentIdx}" class="passengerWrap" data-index="${currentIdx}" style="display:none;">
            <h2><span>${passenger}</span></h2>
        </div>
        <button type="button" id="addPassengerBtn_${currentIdx}" class="addBtn passengerAddBtn" data-index="${currentIdx}" onclick="MozAtesPoliceApp.viewAddPassengerModal(this)"><img src="/images/table-plus.png" class="addImg">${addPassenger}</button>`; 
          		
        const wrap = document.createElement('div');
//        wrap.dataset.partyNo = currentIdx;
        wrap.className= "crackdown-wrap accident-party-wrap";
        wrap.innerHTML = html;
        
        document.querySelector("#accident-party-container").appendChild(wrap);
    }
    
	app.partyAndPassengerReIndexing = function(deletedIndex) {
	    let partyWraps = document.querySelectorAll('.accident-party-wrap');
	    for (let i = deletedIndex; i < partyWraps.length; i++) {
	        let newIndex = i - 1;
	        let partyWrap = partyWraps[i];
	        partyWrap.dataset.index = newIndex;
	        partyWrap.querySelector('.partyClose').dataset.index = newIndex;
	        partyWrap.querySelector('.party_cnt').textContent = newIndex + 1;
	        
	        let inputs = partyWrap.querySelectorAll('input[id], select[id]');
	        inputs.forEach(function(input) {
	            let id = input.id;
	            input.id = id.replace(`_${i}`, `_${newIndex}`);
	            let label = document.querySelector(`label[for="${id}"]`);
	            if (label) label.setAttribute('for', id.replace(`_${i}`, `_${newIndex}`));
	            
	            if(input.hasClass('partyAccidentTarget')){
					input.dataset.index = newIndex;
				}
	        });
	        
	        let addPassengerBtn = partyWrap.querySelector(`#addPassengerBtn_${i}`);
	        
	        if (addPassengerBtn) {
				addPassengerBtn.dataset.index = newIndex;
				addPassengerBtn.id = `addPassengerBtn_${newIndex}`;
			}
			
	        ['vehicle-wrap', 'passenger-wrap'].forEach(function(type) {
	            let wrap = partyWrap.querySelector(`#${type}_${i}`);
	            if (wrap) {
	                wrap.id = `${type}_${newIndex}`;
	                wrap.dataset.index = newIndex;
	            }
	        });
	        
	        passengerReIndexingForParents(i,newIndex);
	    }
	}
	
	app.passengerReIndexingForParents = function(partyIdx, newIdx) {
		let passengerList = document.querySelectorAll(".passenger"+partyIdx);
		let passengerListWrap = document.querySelectorAll(".passenger-list-wrap"+partyIdx);
		let pnrNmStrList = document.querySelectorAll(".pnrNmStr"+partyIdx);
		let drvYnList = document.querySelectorAll(".drvYn"+partyIdx);
		let pnrDvrLcenIdList = document.querySelectorAll(".pnrDvrLcenId"+partyIdx);
		let dvrLcenTyList = document.querySelectorAll(".dvrLcenTy"+partyIdx);
		let pnrNmList = document.querySelectorAll(".pnrNm"+partyIdx);
		let pnrAddrList = document.querySelectorAll(".pnrAddr"+partyIdx);
		let pnrBrthList = document.querySelectorAll(".pnrBrth"+partyIdx);
		let pnrPnoList = document.querySelectorAll(".pnrPno"+partyIdx);
		let pnrEmailList = document.querySelectorAll(".pnrEmail"+partyIdx);
		let pnrDmgCdList = document.querySelectorAll(".pnrDmgCd"+partyIdx);
		let pnrDmgDescList = document.querySelectorAll(".pnrDmgDesc"+partyIdx);
		let pnrDrvrRltnCdList = document.querySelectorAll(".pnrDrvrRltnCd"+partyIdx);
		let pnrDeadYnList = document.querySelectorAll(".pnrDeadYn"+partyIdx);
		
		if(passengerList != null && passengerList.length > 0){
			for(var i = 0; i < passengerList.length; i++){
				let newExtendsName = newIdx+"_"+i;
				let viewPassengerDetailFunc = "MozAtesPoliceApp.viewPassengerDetail('"+newIdx+"','"+i+"')"; 
				
				passengerListWrap[i].setAttribute("id","passenger-list-div"+newExtendsName);
				passengerListWrap[i].setAttribute("onclick", viewPassengerDetailFunc);
				passengerList[i].setAttribute("id", "passenger"+newExtendsName);
				pnrNmStrList[i].setAttribute("id", "pnrNmStr"+newExtendsName);
				drvYnList[i].setAttribute("id", "drvYn"+newExtendsName);
				pnrDvrLcenIdList[i].setAttribute("id", "pnrDvrLcenId"+newExtendsName);
				dvrLcenTyList[i].setAttribute("id", "dvrLcenTy"+newExtendsName);
				pnrNmList[i].setAttribute("id", "pnrNm"+newExtendsName);
				pnrAddrList[i].setAttribute("id", "pnrAddr"+newExtendsName);
				pnrBrthList[i].setAttribute("id", "pnrBrth"+newExtendsName);
				pnrPnoList[i].setAttribute("id", "pnrPno"+newExtendsName);
				pnrEmailList[i].setAttribute("id", "pnrEmail"+newExtendsName);
				pnrDmgCdList[i].setAttribute("id", "pnrDmgCd"+newExtendsName);
				pnrDmgDescList[i].setAttribute("id", "pnrDmgDesc"+newExtendsName);
				pnrDrvrRltnCdList[i].setAttribute("id", "pnrDrvrRltnCd"+newExtendsName);
				pnrDeadYnList[i].setAttribute("id", "pnrDeadYn"+newExtendsName);
				
				
				let passengerDiv = document.getElementById('passenger'+newExtendsName);
				
				passengerDiv.classList.remove(`passenger${partyIdx}`);
				passengerDiv.classList.add(`passenger${newIdx}`);
				
				let removeButtons = passengerDiv.querySelectorAll(`button[data-index='${partyIdx}']`);
				
				removeButtons.forEach(button => {
				    button.dataset.index = newIdx;
				});
				
				document.getElementById('passenger-list-div'+newExtendsName).classList.remove(`passenger-list-wrap${partyIdx}`);
				document.getElementById('passenger-list-div'+newExtendsName).classList.add(`passenger-list-wrap${newIdx}`);
				
				document.getElementById('pnrNmStr'+newExtendsName).classList.remove(`pnrNmStr${partyIdx}`);
				document.getElementById('pnrNmStr'+newExtendsName).classList.add(`pnrNmStr${newIdx}`);

				document.getElementById('drvYn'+newExtendsName).classList.remove(`drvYn${partyIdx}`);
				document.getElementById('drvYn'+newExtendsName).classList.add(`drvYn${newIdx}`);

				document.getElementById('pnrDvrLcenId'+newExtendsName).classList.remove(`pnrDvrLcenId${partyIdx}`);
				document.getElementById('pnrDvrLcenId'+newExtendsName).classList.add(`pnrDvrLcenId${newIdx}`);

				document.getElementById('dvrLcenTy'+newExtendsName).classList.remove(`dvrLcenTy${partyIdx}`);
				document.getElementById('dvrLcenTy'+newExtendsName).classList.add(`dvrLcenTy${newIdx}`);
				
				document.getElementById('pnrNm'+newExtendsName).classList.remove(`pnrNm${partyIdx}`);
				document.getElementById('pnrNm'+newExtendsName).classList.add(`pnrNm${newIdx}`);

				document.getElementById('pnrAddr'+newExtendsName).classList.remove(`pnrAddr${partyIdx}`);
				document.getElementById('pnrAddr'+newExtendsName).classList.add(`pnrAddr${newIdx}`);

				document.getElementById('pnrBrth'+newExtendsName).classList.remove(`pnrBrth${partyIdx}`);
				document.getElementById('pnrBrth'+newExtendsName).classList.add(`pnrBrth${newIdx}`);

				document.getElementById('pnrPno'+newExtendsName).classList.remove(`pnrPno${partyIdx}`);
				document.getElementById('pnrPno'+newExtendsName).classList.add(`pnrPno${newIdx}`);

				document.getElementById('pnrEmail'+newExtendsName).classList.remove(`pnrEmail${partyIdx}`);
				document.getElementById('pnrEmail'+newExtendsName).classList.add(`pnrEmail${newIdx}`);

				document.getElementById('pnrDmgCd'+newExtendsName).classList.remove(`pnrDmgCd${partyIdx}`);
				document.getElementById('pnrDmgCd'+newExtendsName).classList.add(`pnrDmgCd${newIdx}`);

				document.getElementById('pnrDmgDesc'+newExtendsName).classList.remove(`pnrDmgDesc${partyIdx}`);
				document.getElementById('pnrDmgDesc'+newExtendsName).classList.add(`pnrDmgDesc${newIdx}`);

				document.getElementById('pnrDrvrRltnCd'+newExtendsName).classList.remove(`pnrDrvrRltnCd${partyIdx}`);
				document.getElementById('pnrDrvrRltnCd'+newExtendsName).classList.add(`pnrDrvrRltnCd${newIdx}`);

				document.getElementById('pnrDeadYn'+newExtendsName).classList.remove(`pnrDeadYn${partyIdx}`);
				document.getElementById('pnrDeadYn'+newExtendsName).classList.add(`pnrDeadYn${newIdx}`);
			}
		}
	}
    
    app.passengerReIndexing = function(partyIdx){
		let passengerListWrap = document.querySelectorAll(".passenger-list-wrap"+partyIdx);
		let passengerList = document.querySelectorAll(".passenger"+partyIdx);
		let pnrNmStrList = document.querySelectorAll(".pnrNmStr"+partyIdx);
		let drvYnList = document.querySelectorAll(".drvYn"+partyIdx);
		let pnrDvrLcenIdList = document.querySelectorAll(".pnrDvrLcenId"+partyIdx);
		let dvrLcenTyList = document.querySelectorAll(".dvrLcenTy"+partyIdx);
		let pnrNmList = document.querySelectorAll(".pnrNm"+partyIdx);
		let pnrAddrList = document.querySelectorAll(".pnrAddr"+partyIdx);
		let pnrBrthList = document.querySelectorAll(".pnrBrth"+partyIdx);
		let pnrPnoList = document.querySelectorAll(".pnrPno"+partyIdx);
		let pnrEmailList = document.querySelectorAll(".pnrEmail"+partyIdx);
		let pnrDmgCdList = document.querySelectorAll(".pnrDmgCd"+partyIdx);
		let pnrDmgDescList = document.querySelectorAll(".pnrDmgDesc"+partyIdx);
		let pnrDrvrRltnCdList = document.querySelectorAll(".pnrDrvrRltnCd"+partyIdx);
		let pnrDeadYnList = document.querySelectorAll(".pnrDeadYn"+partyIdx);
		
		if(passengerList != null && passengerList.length > 0){
			for(var i = 0; i < passengerList.length; i++){
				let extendsName = partyIdx+"_"+i;
				let viewPassengerDetailFunc = "MozAtesPoliceApp.viewPassengerDetail('"+partyIdx+"','"+i+"')"; 
				
				passengerListWrap[i].setAttribute("id","passenger-list-div"+extendsName);
				passengerListWrap[i].setAttribute("onclick", viewPassengerDetailFunc);
				passengerList[i].setAttribute("id", "passenger"+extendsName);
				pnrNmStrList[i].setAttribute("id", "pnrNmStr"+extendsName);
				drvYnList[i].setAttribute("id", "drvYn"+extendsName);
				pnrDvrLcenIdList[i].setAttribute("id", "pnrDvrLcenId"+extendsName);
				dvrLcenTyList[i].setAttribute("id", "dvrLcenTy"+extendsName);
				pnrNmList[i].setAttribute("id", "pnrNm"+extendsName);
				pnrAddrList[i].setAttribute("id", "pnrAddr"+extendsName);
				pnrBrthList[i].setAttribute("id", "pnrBrth"+extendsName);
				pnrPnoList[i].setAttribute("id", "pnrPno"+extendsName);
				pnrEmailList[i].setAttribute("id", "pnrEmail"+extendsName);
				pnrDmgCdList[i].setAttribute("id", "pnrDmgCd"+extendsName);
				pnrDmgDescList[i].setAttribute("id", "pnrDmgDesc"+extendsName);
				pnrDrvrRltnCdList[i].setAttribute("id", "pnrDrvrRltnCd"+extendsName);
				pnrDeadYnList[i].setAttribute("id", "pnrDeadYn"+extendsName);
			}
		} else {
			document.getElementById("passenger-wrap_"+partyIdx).style.display = "none";
		}
	}
    
    app.deleteAccidentPassenger = function(_this){
		let partyIdx = _this.getAttribute("data-index");
		
		_this.parentElement.remove();
		
		passengerReIndexing(partyIdx);
	}
    
    
	 app.deleteAccidentParty = function(_this) {
	    let partyWrap = document.querySelectorAll(".accident-party-wrap");
	    let partyIdx = _this.getAttribute("data-index");
	    
	    if (partyWrap != null && partyWrap.length >= 2) {
	        partyAndPassengerReIndexing(partyIdx);
	        
	        _this.parentElement.remove();
	    } else {
	        alert("There must be more than one target.");
	    }
	}
    
    app.modifyAccidentPassenger = function(partyIdx , passengerIdx){
		let extendsIdName = partyIdx+"_"+passengerIdx;
		
		let drvYn = document.getElementById("drvYn").value;
		let pnrDvrLcenId = document.getElementById("pnrDvrLcenId").value;
		let dvrLcenTy = document.getElementById("dvrLcenTy").value;
		let pnrNm = document.getElementById("pnrNm").value;
		let pnrAddr = document.getElementById("pnrAddr").value;
		let pnrBrth = document.getElementById("pnrBrth").value;
		let pnrPno = document.getElementById("pnrPno").value;
		let pnrEmail = document.getElementById("pnrEmail").value;
		let pnrDmgCd = document.getElementById("pnrDmgCd").value;
		let pnrDmgDesc = document.getElementById("pnrDmgDesc").value;
		let pnrDrvrRltnCd = document.getElementById("pnrDrvrRltnCd").value;
		let pnrDeadYn = document.getElementById("pnrDeadYn").value;
		
		if(pnrNm == null || pnrNm == ''){
			alert("Please Input Passenger name");
			return;
		}
		
		if(pnrBrth == null || pnrBrth == ''){
			alert("Please Input Passenger Birthday");
			return;
		}
		
		if(pnrPno == null || pnrPno == ''){
			alert("Please Input Passenger Phone Number");
			return;
		}
		
		document.getElementById("pnrNmStr"+extendsIdName).innerHTML = pnrNm;
		document.getElementById("drvYn"+extendsIdName).value = drvYn;
		document.getElementById("pnrDvrLcenId"+extendsIdName).value = pnrDvrLcenId;
		document.getElementById("dvrLcenTy"+extendsIdName).value = dvrLcenTy;
		document.getElementById("pnrNm"+extendsIdName).value = pnrNm;
		document.getElementById("pnrAddr"+extendsIdName).value = pnrAddr;
		document.getElementById("pnrBrth"+extendsIdName).value = pnrBrth;
		document.getElementById("pnrPno"+extendsIdName).value = pnrPno;
		document.getElementById("pnrEmail"+extendsIdName).value = pnrEmail;
		document.getElementById("pnrDmgCd"+extendsIdName).value = pnrDmgCd;
		document.getElementById("pnrDmgDesc"+extendsIdName).value = pnrDmgDesc;
		document.getElementById("pnrDrvrRltnCd"+extendsIdName).value = pnrDrvrRltnCd;
		document.getElementById("pnrDeadYn"+extendsIdName).value = pnrDeadYn;
		
		document.querySelector("#modal-passenger").remove();
	}

    app.addAccidentPassenger = function(partyIdx , passengerIdx) {
		let drvYn = document.getElementById("drvYn").value;
		let pnrDvrLcenId = document.getElementById("pnrDvrLcenId").value;
		let dvrLcenTy = document.getElementById("dvrLcenTy").value;
		let pnrNm = document.getElementById("pnrNm").value;
		let pnrAddr = document.getElementById("pnrAddr").value;
		let pnrBrth = document.getElementById("pnrBrth").value;
		let pnrPno = document.getElementById("pnrPno").value;
		let pnrEmail = document.getElementById("pnrEmail").value;
		let pnrDmgCd = document.getElementById("pnrDmgCd").value;
		let pnrDmgDesc = document.getElementById("pnrDmgDesc").value;
		let pnrDrvrRltnCd = document.getElementById("pnrDrvrRltnCd").value;
		let pnrDeadYn = document.getElementById("pnrDeadYn").value;
		
		if(pnrNm == null || pnrNm == ''){
			alert("Please Input Passenger name");
			return;
		}
		
		if(pnrPno == null || pnrPno == ''){
			alert("Please Input Passenger Phone Number");
			return;
		}

		let html = `
			<button type="button" data-index="${partyIdx}" class="passengerClose" onclick="MozAtesPoliceApp.deleteAccidentPassenger(this)"><img src="/images/close.png"></button>
			<div class="passenger-Info-Wrap">
				<div id="passenger-list-div${partyIdx}_${passengerIdx}" class="passenger-list-wrap${partyIdx}" onclick="MozAtesPoliceApp.viewPassengerDetail('${partyIdx}','${passengerIdx}')">
					<span id="pnrNmStr${partyIdx}_${passengerIdx}" class="pnrNmStr${partyIdx}">${pnrNm}</span>
					<input type="hidden" id="drvYn${partyIdx}_${passengerIdx}" class="drvYn${partyIdx}" value="${drvYn}">
					<input type="hidden" id="pnrDvrLcenId${partyIdx}_${passengerIdx}" class="pnrDvrLcenId${partyIdx}" value="${pnrDvrLcenId}">
					<input type="hidden" id="dvrLcenTy${partyIdx}_${passengerIdx}" class="dvrLcenTy${partyIdx}" value="${dvrLcenTy}">
					<input type="hidden" id="pnrNm${partyIdx}_${passengerIdx}" class="pnrNm${partyIdx}" value="${pnrNm}">
					<input type="hidden" id="pnrAddr${partyIdx}_${passengerIdx}" class="pnrAddr${partyIdx}" value="${pnrAddr}">
					<input type="hidden" id="pnrBrth${partyIdx}_${passengerIdx}" class="pnrBrth${partyIdx}" value="${pnrBrth}">
					<input type="hidden" id="pnrPno${partyIdx}_${passengerIdx}" class="pnrPno${partyIdx}" value="${pnrPno}">
					<input type="hidden" id="pnrEmail${partyIdx}_${passengerIdx}" class="pnrEmail${partyIdx}" value="${pnrEmail}">
					<input type="hidden" id="pnrDmgCd${partyIdx}_${passengerIdx}" class="pnrDmgCd${partyIdx}" value="${pnrDmgCd}">
					<input type="hidden" id="pnrDmgDesc${partyIdx}_${passengerIdx}" class="pnrDmgDesc${partyIdx}" value="${pnrDmgDesc}">
					<input type="hidden" id="pnrDrvrRltnCd${partyIdx}_${passengerIdx}" class="pnrDrvrRltnCd${partyIdx}" value="${pnrDrvrRltnCd}">
					<input type="hidden" id="pnrDeadYn${partyIdx}_${passengerIdx}" class="pnrDeadYn${partyIdx}" value="${pnrDeadYn}">
				</div>
			</div>`;
			
	   let passengerWrap = document.querySelector("#passenger-wrap_"+partyIdx);
	  
	   passengerWrap.style.display = "block";
	   
        const wrap = document.createElement('div');
        
        let idName = "passenger"+partyIdx+"_"+passengerIdx;
        wrap.setAttribute("id",idName);
        wrap.className= "passengerInfo passenger"+partyIdx;
        wrap.innerHTML = html;
        
        passengerWrap.appendChild(wrap);

		document.querySelector("#modal-passenger").remove();
    }
    
    app.offPassengerModal = function(_this){
		document.querySelector("#modal-passenger").remove();
	}
	
	app.viewPassengerDetail = function(partyIdx,passengerIdx){
		let extendsIdName = partyIdx+"_"+passengerIdx;
		
		let drvYn = document.getElementById("drvYn"+extendsIdName).value;
		let pnrDvrLcenId = document.getElementById("pnrDvrLcenId"+extendsIdName).value;
		let dvrLcenTy = document.getElementById("dvrLcenTy"+extendsIdName).value;
		let pnrNm = document.getElementById("pnrNm"+extendsIdName).value;
		let pnrAddr = document.getElementById("pnrAddr"+extendsIdName).value;
		let pnrBrth = document.getElementById("pnrBrth"+extendsIdName).value;
		let pnrPno = document.getElementById("pnrPno"+extendsIdName).value;
		let pnrEmail = document.getElementById("pnrEmail"+extendsIdName).value;
		let pnrDmgCd = document.getElementById("pnrDmgCd"+extendsIdName).value;
		let pnrDmgDesc = document.getElementById("pnrDmgDesc"+extendsIdName).value;
		let pnrDrvrRltnCd = document.getElementById("pnrDrvrRltnCd"+extendsIdName).value;
		let pnrDeadYn = document.getElementById("pnrDeadYn"+extendsIdName).value;
		
		let passengerInfo = new Object();
		passengerInfo.drvYn = drvYn;
		passengerInfo.pnrDvrLcenId = pnrDvrLcenId;
		passengerInfo.dvrLcenTy = dvrLcenTy;
		passengerInfo.pnrNm = pnrNm;
		passengerInfo.pnrAddr = pnrAddr;
		passengerInfo.pnrBrth = pnrBrth;
		passengerInfo.pnrPno = pnrPno;
		passengerInfo.pnrEmail = pnrEmail;
		passengerInfo.pnrDmgCd = pnrDmgCd;
		passengerInfo.pnrDmgDesc = pnrDmgDesc;
		passengerInfo.pnrDrvrRltnCd = pnrDrvrRltnCd;
		passengerInfo.pnrDeadYn = pnrDeadYn;
		
		const modalLoading = loading("Waiting For Add Passenger View").start();
			$.ajax({
	            url: "/tfcacdntmng/trafficAcdntPasnrAddLayer",
	            data: {
	                "partyIdx" : partyIdx,
	                "passengerIdx" : passengerIdx,
	                "passengerInfoStr" : JSON.stringify(passengerInfo)
	            },
	            type: "get",
	            dataType : "html",
	            success: function(html) {
					$('#modal-passenger-wrap').html(html);
					
					document.querySelector("#modal-passenger").style.display = "block";
	            },
	            complete: function(e) {
					modalLoading.end();
	            }
	        });
	}
	
	app.getPartyDriverInfo = function(partyIdx , _this){
		let drvYn = _this.value;
		if(drvYn == 'Y'){
			let driverLicenseId = document.getElementById('driverLicenseId_'+partyIdx).value;
			let acdntTrgtNm = document.getElementById('acdntTrgtNm_'+partyIdx).value;
			let acdntTrgtBrth = document.getElementById('acdntTrgtBrth_'+partyIdx).value;
			let acdntTrgtPno = document.getElementById('acdntTrgtPno_'+partyIdx).value;
			
			document.getElementById('pnrDvrLcenId').value = driverLicenseId;
			document.getElementById('pnrNm').value = acdntTrgtNm;
			document.getElementById('pnrBrth').value = acdntTrgtBrth;
			document.getElementById('pnrPno').value = acdntTrgtPno;
		} else {
			document.getElementById('pnrDvrLcenId').value = "";
			document.getElementById('pnrNm').value = "";
			document.getElementById('pnrBrth').value = "";
			document.getElementById('pnrPno').value = "";
		}
	}

    app.viewAddPassengerModal = function(_this) {
		let partyIdx = _this.getAttribute("data-index");
		let passengerDiv = document.querySelectorAll(".passenger"+partyIdx);
		let passengerIdx = passengerDiv != null ? passengerDiv.length : 0;
		
		const modalLoading = loading("Waiting For Add Passenger View").start();
			$.ajax({
	            url: "/tfcacdntmng/trafficAcdntPasnrAddLayer",
	            data: {
	                "partyIdx" : partyIdx,
	                "passengerIdx" : passengerIdx
	            },
	            type: "get",
	            dataType : "html",
	            success: function(html) {
					$('#modal-passenger-wrap').html(html);
					
					document.querySelector("#modal-passenger").style.display = "block";
	            },
	            complete: function(e) {
					modalLoading.end();
	            }
	        });
	}
    
    
    app.viewVehicleWrap = function(_this) {
		let accidentTrgtType = _this.value;
		let idx = _this.getAttribute("data-index");
		
		//타겟이 차량일때만 차량정보 등록
		if(accidentTrgtType === 'ATT000'){
			document.getElementById('vehicle-wrap_'+idx).style.display = "block";
			document.getElementById('passenger-wrap_'+idx).style.display = "block";
			document.getElementById('addPassengerBtn_'+idx).style.display = "block";
		} else {
			document.getElementById('vehicle-wrap_'+idx).style.display = "none";
			document.getElementById('passenger-wrap_'+idx).style.display = "none";
			document.getElementById('addPassengerBtn_'+idx).style.display = "none";
		}
    }

    app.fileManager = function(){
        let _self = this;
        let fileNo = 0;
        let filesArr = [];
        const maxFileCnt = 5;   // 첨부파일 최대 개수

        this.getFileList = function(){
            return filesArr;
        }
        /* 첨부파일 추가 */
        this.addFile = function(obj, cls){

            let attFileCnt = document.querySelectorAll('.img-file-list.active').length;    // 기존 추가된 첨부파일 개수
            let remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
            let curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

            // 첨부파일 개수 확인
            if (curFileCnt > remainFileCnt) {
                alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
                return;
            }

            for (let i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {
                const file = obj.files[i];
                // 첨부파일 검증
                if (validation(file)) {
                    // 파일 배열에 담기
                    let reader = new FileReader();
                    reader.onload = function () {
                        filesArr.push(file);
                        console.log(file);
                        let htmlData = document.createElement('li');
                        htmlData.className = "img-file-list imgFile";
                        htmlData.dataset.id = fileNo;
                        htmlData.id='image-file-list-'+fileNo;
                        htmlData.addEventListener("click", () => {
                            _self.deleteFile(htmlData.dataset.id);
                        })
                        let imgData = document.createElement('img');
                        imgData.src = reader.result;
                        const target = document.getElementById('img-file-list-wrapper');
                        htmlData.appendChild(imgData)
                        target.appendChild(htmlData);
                        
                        fileNo++;
                    };
                    reader.readAsDataURL(file);
                }
            }
            // 초기화
            document.querySelector("input[type=file]").value = "";
        }

        /* 첨부파일 검증 */
        function validation(obj){
            console.log("obj", obj);
            const fileTypes = ['image/png','image/jpg','image/jpeg','image/tif','image/gif'];
            if (obj.name.length > 100) {
                alert("파일명이 100자 이상인 파일은 제외되었습니다.");
                return false;
            } else if (obj.size > (10 * 1024 * 1024)) {
                alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
                return false;
            } else if (obj.name.lastIndexOf('.') == -1) {
                alert("확장자가 없는 파일은 제외되었습니다.");
                return false;
            } else if (!fileTypes.includes(obj.type)) {
                alert("첨부가 불가능한 파일은 제외되었습니다.");
                return false;
            } else {
                return true;
            }
        }

        /* 첨부파일 삭제 */
        this.deleteFile = function(num) {
            document.querySelector("#image-file-list-" + num).remove();
            filesArr[num].is_delete = true;
        }
        return this;
    }

    return app;
})();