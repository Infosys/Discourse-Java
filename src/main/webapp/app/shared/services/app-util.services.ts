/* eslint-disable @typescript-eslint/tslint/config */
/* eslint-disable @typescript-eslint/camelcase */
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
@Injectable({
  providedIn: 'root',
})
export class AppUtilServices {
  // invokeHamburgerMenu = new EventEmitter();
  // subsVar: Subscription;
  // ripple color
  ripcolor = 'rgba(134,38,195,0.15)';
  constructor(private snackBar: MatSnackBar) {}

  // hamBurgerMenu() {
  //   this.invokeHamburgerMenu.emit();

  //   if(document.getElementById('mainNav').classList.contains("DSA_navHide")){
  //         document.getElementById('mainNav').classList.remove('DSA_navHide');
  //         document.body.style.overflow = 'auto';
  //     }
  //     else{
  //         document.getElementById('mainNav').classList.add('DSA_navHide');
  //         document.body.style.overflow = 'hidden';
  //     }

  // }
  // copy code
  codeCopyFunc(thisElement: any): void {
    const stringVal = thisElement.target.nextElementSibling.innerHTML;
    thisElement.target.nextElementSibling.className += ' ' + 'ds_selectedTxt';
    const whitespace = stringVal.replace(/ +(?= )/g, '');
    const stringrep = whitespace.replace(/&lt;/g, '<');
    const stringrep2 = stringrep.replace(/&gt;/g, '>');

    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = stringrep2;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
  }

  //  showmore DSA_showless

  showMore(thisElement: any): void {
    if (thisElement.target.classList.contains('down-arw-icon')) {
      thisElement.target.closest('.collapseCtr').querySelector('.collapseCnt').classList.add('DSA_showmore');
      thisElement.target.closest('.collapseCtr').querySelector('.collapseCnt').classList.remove('DSA_showless');
      thisElement.target.classList.add('up-arw-icon');
      thisElement.target.classList.remove('down-arw-icon');
    } else {
      thisElement.target.closest('.collapseCtr').querySelector('.collapseCnt').classList.add('DSA_showless');
      thisElement.target.closest('.collapseCtr').querySelector('.collapseCnt').classList.remove('DSA_showmore');
      thisElement.target.classList.add('down-arw-icon');
      thisElement.target.classList.remove('up-arw-icon');
    }
  }

  // toggle class
  // eslint-disable-next-line @typescript-eslint/camelcase
  chip_toggleClick(thisElement: any): void {
    if (thisElement.target.classList.contains('active')) {
      thisElement.target.classList.remove('active');
    } else {
      thisElement.target.classList.add('active');
    }
  }

  // toggle class

  // eslint-disable-next-line @typescript-eslint/camelcase
  chipIcon_toggleSelect(thisElement: any): void {
    if (thisElement.target.classList.contains('DSA_choiseChip')) {
      thisElement = thisElement.target;
    } else {
      thisElement = thisElement.target.closest('.DSA_choiseChip');
    }
    if (thisElement.querySelector('.DSA_selectionIcon').closest('.DSA_choiseChip').classList.contains('active')) {
      thisElement.querySelector('.DSA_selectionIcon').closest('.DSA_choiseChip').classList.remove('active');
    } else {
      thisElement.querySelector('.DSA_selectionIcon').closest('.DSA_choiseChip').classList.add('active');
    }
    /* if( thisElement.querySelector(".DSA_selectionIcon").classList.contains('icon-process-complete')){
          thisElement.querySelector(".DSA_selectionIcon").closest('.DSA_choiseChip').classList.remove('active');
          thisElement.querySelector(".DSA_selectionIcon").classList.remove('icon-process-complete');
          thisElement.querySelector(".DSA_selectionIcon").classList.remove('purple');
        }
        else{
          thisElement.querySelector(".DSA_selectionIcon").closest('.DSA_choiseChip').classList.add('active');
          thisElement.querySelector(".DSA_selectionIcon").classList.add('icon-process-complete');
          thisElement.querySelector(".DSA_selectionIcon").classList.add('purple');
        } */
  }

  // chip Close button
  chip_removeParent(closebtn: any): void {
    closebtn.target.parentNode.remove();
  }

  expandCollapseMenu(eventPanel: any): void {
    // eventPanel.target.closest('.dc_TreeulClass').querySelector('.collapseCnt')
    if (eventPanel.target.nextSibling.style.display === 'block' || eventPanel.target.nextSibling.style.display === '') {
      eventPanel.target.nextSibling.style.display = 'none';
    } else {
      eventPanel.target.nextSibling.style.display = 'block';
    }
  }

  lefttwoLevelMenu(thisElement: any): void {
    const listItems = document.querySelectorAll('.dc_ToplevelMenu a');
    listItems.forEach(function (eachList) {
      eachList.classList.remove('active');
    });

    thisElement.target.classList.add('active');
    if (thisElement.target.classList.contains('dc_secondlevelLI')) {
      thisElement.target.closest('li').querySelector('.dc_secondlevelUL li:first-child a').classList.add('active');
    } else if (thisElement.target.closest('ul').classList.contains('dc_secondlevelUL')) {
      thisElement.target.closest('ul').previousSibling.classList.add('active');
    }

    if (!thisElement.target.closest('ul').classList.contains('dc_secondlevelUL')) {
      const allmenus = thisElement.target.closest('.dc_ToplevelMenu').querySelectorAll('.dc_TreeulClass');
      allmenus.forEach((eachMenu: any) => {
        eachMenu.classList.add('d-none');
      });
      if (thisElement.target.nextSibling) {
        thisElement.target.nextSibling.classList.remove('d-none');
      }
    }
  }

  /* hamburdermenu */
  DSA_mainHamburger_Click(eventObj: any) {
    eventObj.stopPropagation();
    const mainNav = document.getElementById('mainNav');
    const mainNavClose = document.getElementById('mainNavClose');
    if (mainNav != null && mainNavClose != null) {
      if (mainNav.classList.contains('DSA_navHide')) {
        mainNav.classList.remove('DSA_navHide');
        document.body.style.overflow = 'auto';
        mainNavClose.focus();
      } else {
        mainNav.classList.add('DSA_navHide');
        document.body.style.overflow = 'hidden';
        mainNav.focus();
      }
    }
  }
  /* lft rmenu */
  DSA_mainMenu_Click() {
    const mainleftNav = document.getElementById('mainleftNav');
    if (mainleftNav != null) {
      if (mainleftNav.classList.contains('DSA_navHideMenu')) {
        mainleftNav.classList.remove('DSA_navHideMenu');
        const queryA = mainleftNav.querySelector('a');
        if (queryA != null) {
          queryA.focus();
        }
        document.body.style.overflow = 'auto';
      } else {
        mainleftNav.classList.add('DSA_navHideMenu');
        document.body.style.overflow = 'hidden';
      }
    }
  }
  /* mobile search */
  DSA_mainSearch_Click() {
    const mainSearch = document.getElementById('mainSearch');
    const mainHeader = document.getElementById('mainHeader');
    if (mainSearch != null && mainHeader != null) {
      if (mainSearch.classList.contains('DSA_navHideSrch')) {
        mainHeader.style.display = 'none';
        mainSearch.classList.remove('DSA_navHideSrch');
      } else {
        mainHeader.style.display = 'block';
        mainSearch.classList.add('DSA_navHideSrch');
      }
    }
  }

  /* floatingright panel*/

  DSA_toggleRightpanel_Click() {
    const coloumnthreeFLoating = document.getElementById('coloumnthreeFLoating');
    const fLoatingClose = document.getElementById('fLoatingClose');
    const togglepanelButton = document.getElementById('togglepanelButton');
    if (coloumnthreeFLoating != null && togglepanelButton != null && fLoatingClose != null) {
      if (coloumnthreeFLoating.classList.contains('DSA_rightPanelHide')) {
        coloumnthreeFLoating.classList.remove('DSA_rightPanelHide');
        fLoatingClose.focus();
      } else {
        coloumnthreeFLoating.classList.add('DSA_rightPanelHide');
        document.querySelectorAll('.DSA_RightUtil li').forEach(function (eachList) {
          eachList.classList.remove('DSA_active');
        });
        togglepanelButton.focus();
      }
    }
  }

  /* content area height */
  middleHeight() {
    const windowHeight = window.innerHeight;
    const windowWidth = window.innerWidth;
    const footerHeight = 40;
    let headerOuterHeight;
    if (windowWidth < 1024) {
      headerOuterHeight = 56;
    } else {
      headerOuterHeight = 50;
    }
    const contentHt = windowHeight - (headerOuterHeight + footerHeight);
    const mainCont = document.getElementById('mainCtr');
    if (mainCont) {
      mainCont.style.paddingTop = headerOuterHeight + 'px';
    }

    const mainContentArea = document.getElementById('mainrightCnt');
    if (mainContentArea) {
      mainContentArea.style.minHeight = contentHt + 'px';
      // mainContentArea.style.paddingTop = (headerOuterHeight) + 'px';
    }

    const leftMenuArea = document.getElementById('mainleftNav');
    if (leftMenuArea) {
      leftMenuArea.style.minHeight = contentHt + 'px';
      leftMenuArea.style.top = headerOuterHeight + 'px';
    }
    const mainContentArea2 = document.getElementById('coloumnthree');
    if (mainContentArea2) {
      mainContentArea2.style.minHeight = contentHt + 'px';
      mainContentArea2.style.top = headerOuterHeight + 'px';
    }
    const mainContentArea2Float = document.getElementById('coloumnthreeFLoating');
    if (mainContentArea2Float) {
      mainContentArea2Float.style.minHeight = contentHt + 'px';
      // mainContentArea2Float.style.paddingTop = (headerOuterHeight) + 'px';
    }
  }

  loginHeight() {
    const windowHeight = window.innerHeight;
    const windowWidth = window.innerWidth;
    const loginContentArea = document.getElementById('DSA_pageoverlay');
    // var loginoverlay = document.getElementById('DSA_pageoverlay');

    const loginContentmidpanel = document.getElementById('DSA_pageMidpanel');
    const logoMid = document.getElementById('DSA_logoMid');
    if (loginContentmidpanel && logoMid && loginContentArea) {
      const midpanel = loginContentmidpanel.offsetHeight;
      const logoMidpanel = logoMid.offsetHeight;
      const contentHt = windowHeight;
      const panelpad = windowHeight / 2 - midpanel / 2;
      const logopad = windowHeight / 2 - logoMidpanel / 2;
      // loginoverlay.style.height = (contentHt) + 'px';
      if (windowWidth < 1024) {
        loginContentArea.style.minHeight = '';
        loginContentmidpanel.style.marginTop = '';
        logoMid.style.marginTop = '';
      } else {
        loginContentArea.style.minHeight = contentHt + 'px';
        loginContentmidpanel.style.marginTop = panelpad + 'px';
        logoMid.style.marginTop = logopad + 'px';
      }
    }
  }

  /* menu set active*/
  setActiveClass(eventObj: any): void {
    const listItems = eventObj.target.closest('ul.DSA_frstLevel').querySelectorAll('.active');
    const listItems2 = eventObj.target.closest('ul').querySelectorAll('.active');
    const listItems3 = eventObj.target.closest('ul.DSA_frstLevel').querySelectorAll('.activeDark');
    if (eventObj.target.closest('ul').classList.contains('DSA_secLevel')) {
      listItems.forEach(function (eachList: any) {
        eachList.classList.remove('active');
      });
      listItems3.forEach(function (eachList: any) {
        eachList.classList.remove('activeDark');
      });
      eventObj.target.closest('ul').parentNode.classList.add('activeDark');
      eventObj.target.closest('li').classList.add('active');
    } else {
      listItems2.forEach(function (eachList: any) {
        eachList.classList.remove('active');
      });

      listItems3.forEach(function (eachList: any) {
        eachList.classList.remove('activeDark');
      });
      eventObj.target.closest('li').classList.add('active');
      if (eventObj.target.closest('li').querySelector('.DSA_secLevel')) {
        if (eventObj.target.closest('li').querySelector('.DSA_secLevel').classList.contains('hide')) {
          eventObj.target.closest('li').querySelector('.ds_menuArrow').classList.add('down-arw-icon');
          eventObj.target.closest('li').querySelector('.ds_menuArrow').classList.remove('next-icon');
          eventObj.target.closest('li').querySelector('ul').classList.remove('hide');
        } else {
          eventObj.target.closest('li').classList.remove('active');
          eventObj.target.closest('li').querySelector('.ds_menuArrow').classList.add('next-icon');
          eventObj.target.closest('li').querySelector('.ds_menuArrow').classList.remove('down-arw-icon');
          eventObj.target.closest('li').querySelector('ul').classList.add('hide');
        }
      }
    }
  }

  setActiveClass_TopNav(eventObj: any) {
    eventObj.stopPropagation();
    const listItems = eventObj.target.closest('ul.DSA_frstLevel').querySelectorAll('.active');
    const listItems2 = eventObj.target.closest('ul').querySelectorAll('.active');
    const listItems3 = eventObj.target.closest('ul.DSA_frstLevel').querySelectorAll('.activeDark');
    if (eventObj.target.closest('ul').classList.contains('DSA_secLevel')) {
      listItems.forEach(function (eachList: any) {
        eachList.classList.remove('active');
      });
      listItems3.forEach(function (eachList: any) {
        eachList.classList.remove('activeDark');
      });

      eventObj.target
        .closest('ul')
        .parentNode.parentNode.querySelectorAll('ul.DSA_secLevel')
        .forEach(function (eachList: any) {
          if (!eachList.classList.contains('hide')) {
            eachList.classList.add('hide');
          }
        });
      eventObj.target.closest('ul').parentNode.classList.add('activeDark');
      eventObj.target.closest('li').classList.add('active');
      eventObj.target.closest('ul.DSA_secLevel').classList.remove('hide');
    } else {
      listItems2.forEach(function (eachList: any) {
        eachList.classList.remove('active');
      });

      listItems3.forEach(function (eachList: any) {
        eachList.classList.remove('activeDark');
      });
      eventObj.target.closest('li').classList.add('active');
      if (eventObj.target.closest('li').querySelector('.DSA_secLevel')) {
        if (!eventObj.target.closest('li').querySelector('.DSA_secLevel').classList.contains('hide')) {
          /* if(!eventObj.target.closest('li').querySelector('.DSA_secLevel').classList.contains('DSA_wb_topMenu_sec')) {
              eventObj.target.closest('li').classList.remove('active');            
              eventObj.target.closest('li').querySelector('ul').classList.add('hide');
            }  */
        } else {
          eventObj.target
            .closest('li')
            .parentNode.querySelectorAll('ul.DSA_secLevel')
            .forEach(function (eachList: any) {
              if (!eachList.classList.contains('hide')) {
                eachList.classList.add('hide');
              }
            });
          eventObj.target.closest('li').querySelector('ul').classList.remove('hide');
        }
      } else {
        eventObj.target
          .closest('li')
          .parentNode.querySelectorAll('ul.DSA_secLevel')
          .forEach(function (eachList: any) {
            if (!eachList.classList.contains('hide')) {
              eachList.classList.add('hide');
            }
          });
      }
    }
  }

  selectCardService(eventObj: any) {
    if (eventObj.target.closest('.DSA_selectionCard').classList.contains('DSA_active')) {
      eventObj.target.closest('.DSA_selectionCard').classList.remove('DSA_active');
    } else {
      eventObj.target.closest('.DSA_selectionCard').classList.add('DSA_active');
    }
  }
  onResizeService() {
    this.loginHeight();
  }

  /* chage view */
  changeViewService(thisElement: any, designViewParam: any) {
    const codeView = document.querySelector('.codeView');
    const designView = document.querySelector('.designView');
    if (codeView && designView) {
      if (designViewParam) {
        codeView.classList.remove('active');
        codeView.classList.add('hide');
        designView.classList.remove('hide');
        designView;
        designView.classList.add('active');
        thisElement.target.parentElement.classList.add('active');
        thisElement.target.parentElement.previousSibling.classList.remove('active');
      } else {
        designView.classList.remove('active');
        designView.classList.add('hide');
        codeView.classList.remove('hide');
        codeView.classList.add('active');
        thisElement.target.parentElement.classList.add('active');
        thisElement.target.parentElement.nextSibling.classList.remove('active');
      }
    }
  }

  /* timepicker border*/
  setInputFocusService(eventObj: any) {
    eventObj.target.closest('.DCA_wb_cusTimeCtr').classList.add('mat-focused');
  }
  resetsetInputFocusService(eventObj: any) {
    eventObj.target.closest('.DCA_wb_cusTimeCtr').classList.remove('mat-focused');
  }

  setCrossLinkUrl(urlObj: any) {
    const urlLink = '/';
    const branding = 'innerPages/branding/';
    const components = 'innerPages/components/';
    const pages = 'innerPages/Pages/';

    switch (urlObj) {
      case 'ColorsandStyleComponent':
        window.open(urlLink + branding + 'colorsand-style', '_blank');
        break;
      case 'TypographyComponent':
        window.open(urlLink + branding + 'typography', '_blank');
        break;
      case 'ToneOfVoiceComponent':
        window.open(urlLink + branding + 'tone-of-voice', '_blank');
        break;
      case 'NamingComponent':
        window.open(urlLink + branding + 'naming', '_blank');
        break;
      case 'PersonalityComponent':
        window.open(urlLink + branding + 'personality', '_blank');
        break;
      case 'UsabilityComponent':
        window.open(urlLink + branding + 'usability', '_blank');
        break;
      case 'GridsAndSpacingComponent':
        window.open(urlLink + branding + 'grids-and-spacing', '_blank');
        break;

      case 'ButtonsComponent':
        window.open(urlLink + components + 'buttons', '_blank');
        break;
      case 'SelectionControlsComponent':
        window.open(urlLink + components + 'selection-controls', '_blank');
        break;
      case 'InputFieldComponent':
        window.open(urlLink + components + 'input-field', '_blank');
        break;
      case 'DatePickerComponent':
        window.open(urlLink + components + 'date-picker', '_blank');
        break;
      case 'ChipsComponent':
        window.open(urlLink + components + 'chips', '_blank');
        break;
      case 'CardsComponent':
        window.open(urlLink + components + 'cards', '_blank');
        break;
      case 'ListComponent':
        window.open(urlLink + components + 'list', '_blank');
        break;
      case 'DialogComponent':
        window.open(urlLink + components + 'dialog', '_blank');
        break;
      case 'ModalComponent':
        window.open(urlLink + components + 'modal', '_blank');
        break;
      case 'ChartandgraphComponent':
        window.open(urlLink + components + 'chartandgraph', '_blank');
        break;
      case 'TabsComponent':
        window.open(urlLink + components + 'tabs', '_blank');
        break;
      case 'ExpandCollapseComponent':
        window.open(urlLink + components + 'Expand-collapse', '_blank');
        break;
      case 'AttachmentComponent':
        window.open(urlLink + components + 'attachment', '_blank');
        break;
      case 'CommentComponent':
        window.open(urlLink + components + 'comment', '_blank');
        break;
      case 'BottomSheetComponent':
        window.open(urlLink + components + 'bottom-sheet', '_blank');
        break;
      case 'OverflowMenuComponent':
        window.open(urlLink + components + 'overflow-menu', '_blank');
        break;
      case 'ProfileBannerComponent':
        window.open(urlLink + components + 'profile-banner', '_blank');
        break;
      case 'AddressComponent':
        window.open(urlLink + components + 'address', '_blank');
        break;
      case 'TimelineComponent':
        window.open(urlLink + components + 'timeline', '_blank');
        break;
      case 'BottomBarsComponent':
        window.open(urlLink + components + 'bottom-bars', '_blank');
        break;
      case 'TopBarsComponent':
        window.open(urlLink + components + 'top-bars', '_blank');
        break;
      case 'HamburgerComponent':
        window.open(urlLink + components + 'hamburger', '_blank');
        break;
      case 'FiltersComponent':
        window.open(urlLink + components + 'filters', '_blank');
        break;
      case 'MessagingComponent':
        window.open(urlLink + components + 'messaging', '_blank');
        break;
      case 'ConstructsComponent':
        window.open(urlLink + components + 'constructs', '_blank');
        break;
      case 'NotificationComponent':
        window.open(urlLink + components + 'notification', '_blank');
        break;
      case 'NudgesComponent':
        window.open(urlLink + components + 'nudges', '_blank');
        break;
      case 'MicroFeedbackComponent':
        window.open(urlLink + components + 'micro-feedback', '_blank');
        break;
      case 'GuidedFlowsComponent':
        window.open(urlLink + components + 'guided-flows', '_blank');
        break;
      case 'VocabularyComponent':
        window.open(urlLink + components + 'vocabulary', '_blank');
        break;
      case 'BannerComponent':
        window.open(urlLink + components + 'banner', '_blank');
        break;
      case 'EmptyStateComponent':
        window.open(urlLink + components + 'empty-state', '_blank');
        break;
      case 'SnackbarComponent':
        window.open(urlLink + components + 'snackbar', '_blank');
        break;
      case 'DataGridComponent':
        window.open(urlLink + components + 'data-grid', '_blank');
        break;
      case 'BadgeComponent':
        window.open(urlLink + components + 'badge', '_blank');
        break;
      case 'GlobalHeaderComponent':
        window.open(urlLink + components + 'global-header', '_blank');
        break;
      case 'WebfooterComponent':
        window.open(urlLink + components + 'webfooter', '_blank');
        break;
      case 'PaginationComponent':
        window.open(urlLink + components + 'pagination', '_blank');
        break;
      case 'PopoverComponent':
        window.open(urlLink + components + 'popover', '_blank');
        break;
      case 'TooltipComponent':
        window.open(urlLink + components + 'tooltip', '_blank');
        break;
      case 'CalloutComponent':
        window.open(urlLink + components + 'callout', '_blank');
        break;
      case 'SliderComponent':
        window.open(urlLink + components + 'slider', '_blank');
        break;

      case 'PageFormpageComponent':
        window.open(urlLink + pages + 'page-formpage', '_blank');
        break;
      case 'FormpageSimpleComponent':
        window.open(urlLink + pages + 'formpage-simple', '_blank');
        break;
      case 'FormpageMediumComponent':
        window.open(urlLink + pages + 'formpage-medium', '_blank');
        break;
      case 'FormpageComplexComponent':
        window.open(urlLink + pages + 'formpage-complex', '_blank');
        break;
    }
  }

  bindEvent(el: any, eventName: any, eventHandler: any) {
    if (el.addEventListener) {
      el.addEventListener(eventName, eventHandler, false);
    } else if (el.attachEvent) {
      el.attachEvent('on' + eventName, eventHandler);
    }
  }

  DSpopoverbind(thisElement: any): void {
    const DSA_popoverMainBody = document.querySelector('.DSA_popoverMainBody') as HTMLElement;
    if (DSA_popoverMainBody && thisElement.type === 'mouseenter') {
      if (DSA_popoverMainBody && DSA_popoverMainBody.parentNode) {
        DSA_popoverMainBody.parentNode.removeChild(DSA_popoverMainBody);
      }

      const rect = thisElement.target.getBoundingClientRect();

      const iDiv = document.createElement('div');
      iDiv.id = 'DSA_popoverMainBody';
      iDiv.className = 'DSA_popoverMainBody';
      document.getElementsByTagName('body')[0].appendChild(iDiv);
      const cln = thisElement.target.closest('.DSA_popoverMainCtr').querySelector('.DSA_popoverInner').cloneNode(true);
      let qc = document.querySelector('.DSA_popoverMainBody');
      if (qc) {
        qc.appendChild(cln);
      }
      qc = DSA_popoverMainBody.querySelector('.DSA_popoverclose');
      if (qc) {
        qc.id = 'DSA_popoverclose';
      }

      const wdth = DSA_popoverMainBody.offsetWidth;
      const hgth = DSA_popoverMainBody.offsetHeight;
      const itemwdth = thisElement.target.offsetWidth;
      const itemhgth = thisElement.target.offsetHeight;

      if (thisElement.target.getAttribute('data-Pos') === 'left') {
        DSA_popoverMainBody.style.left = rect.left - 20 - wdth + 'px';
        DSA_popoverMainBody.style.top = rect.top - (hgth - itemhgth) / 2 + 'px';
        DSA_popoverMainBody.classList.add('DSA_leftArrow');
      }
      if (thisElement.target.getAttribute('data-Pos') === 'right') {
        DSA_popoverMainBody.style.top = rect.top - (hgth - itemhgth) / 2 + 'px';
        DSA_popoverMainBody.style.left = rect.right + 10 + 'px';
        DSA_popoverMainBody.classList.add('DSA_rightArrow');
      }
      if (thisElement.target.getAttribute('data-Pos') === 'top') {
        DSA_popoverMainBody.style.top = rect.top - itemhgth - 10 - hgth + 'px';
        DSA_popoverMainBody.classList.add('DSA_topArrow');
        DSA_popoverMainBody.style.left = rect.left + (itemwdth - wdth) / 2 + 'px';
      }
      if (thisElement.target.getAttribute('data-Pos') === 'bottom') {
        DSA_popoverMainBody.style.top = rect.top + itemhgth + 10 + 'px';
        DSA_popoverMainBody.classList.add('DSA_bottomArrow');
        DSA_popoverMainBody.style.left = rect.left + (itemwdth - wdth) / 2 + 'px';
      }

      /* this.bindEvent(DSA_popoverMainBody, 'click', function (e) {
            document.getElementById("DSA_popoverMainBody").parentNode.removeChild(DSA_popoverMainBody);
          });*/
      this.bindEvent(DSA_popoverMainBody, 'mouseleave', function () {
        if (DSA_popoverMainBody.parentNode) {
          DSA_popoverMainBody.parentNode.removeChild(DSA_popoverMainBody);
        }
      });
      this.bindEvent(document.getElementById('DSA_popoverclose'), 'click', function () {
        if (DSA_popoverMainBody.parentNode) {
          DSA_popoverMainBody.parentNode.removeChild(DSA_popoverMainBody);
        }
      });
    } else if (thisElement.type === 'mouseleave') {
      /* DO NOTHING*/
    }
  }

  /* top menu right util active*/
  clickactiveClass(eventObj: any) {
    if (eventObj.target.closest('li').classList.contains('DSA_active')) {
      eventObj.target.closest('li').classList.remove('DSA_active');
    } else {
      const listItems = eventObj.target.closest('ul').querySelectorAll('li');
      listItems.forEach(function (eachList: any) {
        eachList.classList.remove('DSA_active');
      });
      // eventObj.target.closest('ul').querySelector('li').classList.remove('DSA_active');
      eventObj.target.closest('li').classList.add('DSA_active');
    }
  }

  openSnackBar(message: string, action: string, duration1 = 3000) {
    this.snackBar.open(message, action, {
      duration: duration1,
      panelClass: ['purple-snackbar'],
    });
  }
}
