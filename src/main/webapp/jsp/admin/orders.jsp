<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.orders"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.css"/>
    <link rel="stylesheet" href="https://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.skin.css"/>
</head>

<body id="page-top">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead" id="mastHeadCars">
    <div class="intro-body">
        <div class="row register-form" id="carPage">
            <div class="col-3">
                <form action="process_controller" method="post" class="shadow-none custom-form" id="carsFilter"
                      style="font-family: Nunito">
                    <div class="form-row form-group">
                        <div class="col-4 label-column">
                            <label class="col-form-label"><fmt:message key="admin_orders.car"/></label></div>
                        <div class="col-7 dropdown input-column">
                            <input class="form-control" name="model" type="text" id="chooseCar"
                                   pattern="^[a-zA-Z0-9 -]{2,100}$">
                        </div>
                        <div class="col-4 label-column">
                            <label class="col-form-label"><fmt:message key="admin_orders.client_email"/></label></div>
                        <div class="col-7 dropdown input-column">
                            <input class="form-control" name="email" type="email" id="clientEmail" minlength="7"
                                   maxlength="255">
                        </div>
                        <div class="col-4 label-column">
                            <label class="col-form-label"><fmt:message key="admin_orders.order_status"/></label></div>
                        <div class="col-7 dropdown input-column">
                            <select class="form-control" name="order_status" id="orderSelect">
                                <option value=""><fmt:message key="admin_orders.status.any"/></option>
                                <option value="PENDING"><fmt:message key="admin_orders.status.pending"/></option>
                                <option value="AWAITING_PAYMENT"><fmt:message
                                        key="admin_orders.status.awaiting_payment"/></option>
                                <option value="ACTIVE"><fmt:message key="admin_orders.status.active"/></option>
                                <option value="COMPLETED"><fmt:message key="admin_orders.status.completed"/></option>
                                <option value="DECLINED"><fmt:message key="admin_orders.status.declined"/></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-row form-group">
                        <div class="col-4 input-column" id="filterbutton">
                            <button class="submit-button" type="submit" id="butt">
                                <fmt:message key="admin_orders.filter"/></button>
                        </div>
                    </div>
                    <input type="hidden" name="command" value="find_check_orders">
                </form>
            </div>
            <!----------------------------------------------->
            <div class="col-8">
                <div class="form-row form-group register-form" id="orderElement">
                    <c:choose>
                    <c:when test="${not empty sessionScope.orderList}">
                        <table class="table table-sm" id="ordersListTable">
                            <tbody>
                            <tr>
                                <th align="center" name="email"><fmt:message key="admin_orders.table.email"/></th>
                                <th align="center"><fmt:message key="admin_orders.table.client_name"/></th>
                                <th align="center"><fmt:message key="admin_orders.table.car_model"/></th>
                                <th align="center"><fmt:message key="admin_orders.table.date_from"/></th>
                                <th align="center"><fmt:message key="admin_orders.table.date_to"/></th>
                                <th align="center"><fmt:message key="admin_orders.table.amount"/></th>
                                <th align="center" colspan="2"><fmt:message key="admin_orders.table.status"/></th>
                            </tr>
                            <c:forEach var="orderElement" items="${sessionScope.orderList}" varStatus="loop">
                                <form action="process_controller" method="post" class="shadow-lg custom-form">
                                    <tr>
                                        <td name="email" onclick="test(this);">${orderElement.client.email}</td>
                                        <td>${orderElement.client.firstName} ${orderElement.client.secondName}</td>
                                        <td name="car_model" onclick="cartest(this);">${orderElement.car.model}</td>
                                        <td>${orderElement.dateFrom}</td>
                                        <td>${orderElement.dateTo}</td>
                                        <td>${orderElement.amount}</td>
                                        <td>${orderElement.status.name()}</td>
                                        <c:if test="${orderElement.status.name()=='PENDING'|| orderElement.status.name()=='AWAITING_PAYMENT'}">
                                            <td>
                                                <label class="custom-form">
                                                    <input type="hidden" name="order_index"
                                                           value=${loop.index}>
                                                    <input type="hidden" name="command"
                                                           value="update_order_property">
                                                    <input type="hidden" name="status"
                                                           value="DECLINED">
                                                    <button class="submit-button" type="submit"
                                                            id="declineOrder">
                                                            <fmt:message key="admin_orders.table.decline"/>
                                                </label>
                                            </td>
                                        </c:if>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                    <c:if test="${empty noOrders}">
                    <label class="col-form-label"><fmt:message key="admin_orders.choose_params"/></label></div>
                </c:if>
                <c:if test="${not empty noOrders && !noOrders}">
                <label class="col-form-label"><fmt:message key="admin_orders.nothing_to_show"/></label></div>
            </c:if>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/date_range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/grayscale.js"></script>
<script src="${pageContext.request.contextPath}/js/moment.js"></script>
<script src="${pageContext.request.contextPath}/js/tablevalue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</body>
</html>


